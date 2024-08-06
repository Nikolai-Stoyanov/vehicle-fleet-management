import {Component, OnDestroy, OnInit} from '@angular/core';

import { NzModalService } from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';

import { TableColumnInterface } from '../../../shared/dummy-table';

import { CarRecordService } from '../car-record.service';
import { CarRecordFormComponent } from '../car-record-form';
import {CarRecordList} from "../car-record";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-car-record-list',
  templateUrl: './car-record-list.component.html',
  styleUrls: ['./car-record-list.component.scss'],
})
export class CarRecordListComponent implements OnInit , OnDestroy{
  public currentItems: CarRecordList[]=[];
  public options: string[] = [];
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;
  public currentItem: any;
  public subscriptions: Subscription[] = [];

  constructor(

    private svc: CarRecordService,
    private message: NzMessageService,
    private modalService: NzModalService
  ) {}

  ngOnInit() {
    this.subscriptions.push( this.svc.getColumns().subscribe((res) => {
      this.allTableColumns = res;
    }));
    this.loading = true;
    this.subscriptions.push(this.svc.fetchLatestRecords().subscribe((res:any) => {
      this.currentItems = res;
      this.loading = false;
    }));
  }

  editRecord(item?: CarRecordList) {
    let title;
    if (item) {
      title = $localize`Record: ${item.registrationNumber}`;
    } else {
      title = $localize`New Record`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: CarRecordFormComponent,
      nzWidth: '75vw',
      nzStyle: { top: '0' },
      nzData: {
        id: item?.id,
      },
      nzFooter: null,
    });
    this.subscriptions.push(modal.afterClose.subscribe(() => {
      this.svc.fetchLatestRecords().subscribe((res:CarRecordList[]) => {
        this.currentItems = res;
      });
    }));
  }

  removeRecord() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this model?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        this.subscriptions.push( this.svc.deleteRecord(this.currentItem?.id).subscribe({
          next: (res) => {
            this.message.success(res.message);
            this.subscriptions.push( this.svc.fetchLatestRecords().subscribe((res:CarRecordList[]) => {
              this.currentItems = res;
            }));
          },
          error: (error) => {
            this.message.error(error.status + ' ' + error.error.message);
          }
        }));
      },
      nzCancelText: $localize`No`,
      nzOnCancel: () => {
      }
    });
  }

  currentItemSelect(item: CarRecordList) {
    if (item.id===this.currentItem?.id){
      this.currentItem=undefined
    }else {
      this.currentItem = item;
    }
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
