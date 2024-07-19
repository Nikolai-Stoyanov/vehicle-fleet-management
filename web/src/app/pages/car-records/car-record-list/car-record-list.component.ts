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
    this.svc.fetchLatest().subscribe((res) => {
      this.currentItems = res;
      this.loading = false;
    });
  }

  editRecord(item?: CarRecordList) {
    let title;
    if (item) {
      title = `Record: ${item.registrationNumber}`;
    } else {
      title = `New Record`;
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
      if (res) {
        this.message.info('Функцията не е имплементирана!');
      }
    });
  }

  removeRecord() {
    this.modalService.confirm({
      nzTitle: `Are you sure you want to delete this model?`,
      nzOkText: `Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deleteRecord(this.currentItem?.id).subscribe({
          next: () => {
            this.message.create('success', `Car record successfully deleted.`);
            this.svc.fetchLatest();
          },
          error: (error) => {
            if (error.status === 404) {
              this.message.error(`Record not found`);
            } else {
              this.message.error(error);
            }
          }
        });
      },
      nzCancelText: `No`,
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