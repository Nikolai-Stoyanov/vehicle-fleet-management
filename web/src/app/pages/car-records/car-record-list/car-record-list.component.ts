import {  Component, OnInit } from '@angular/core';

import { NzModalService } from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';

import { TableColumnInterface } from '../../../shared/dummy-table';

import { CarRecordService } from '../car-record.service';
import { CarRecordFormComponent } from '../car-record-form';
import {CarRecordList} from "../car-record";

@Component({
  selector: 'vfm-car-record-list',
  templateUrl: './car-record-list.component.html',
  styleUrls: ['./car-record-list.component.scss'],
})
export class CarRecordListComponent implements OnInit {
  public currentItems: CarRecordList[]=[];
  public options: string[] = [];
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;
  public currentItem: any;

  constructor(

    private svc: CarRecordService,
    private message: NzMessageService,
    private modalService: NzModalService
  ) {}

  ngOnInit() {
    this.svc.getColumns().subscribe((res) => {
      this.allTableColumns = res;
    });
    this.loading = true;
    this.svc.fetchLatestRecords().subscribe((res:any) => {
      this.currentItems = res;
      this.loading = false;
    });
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
        currentItem: item?.id,
      },
      nzFooter: null,
    });
    modal.afterClose.subscribe((res) => {
      this.svc.fetchLatestRecords().subscribe((res:CarRecordList[]) => {
        this.currentItems = res;
      });
    });
  }

  removeRecord() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this model?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deleteRecord(this.currentItem?.id).subscribe({
          next: () => {
            this.message.create('success', $localize`Car record successfully deleted.`);
            this.svc.fetchLatestRecords().subscribe((res:CarRecordList[]) => {
              this.currentItems = res;
            });
          },
          error: (error) => {
            if (error.status === 404) {
              this.message.error($localize`Record not found`);
            } else {
              this.message.error(error);
            }
          }
        });
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
}
