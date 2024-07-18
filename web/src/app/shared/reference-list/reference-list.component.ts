import {
  Component,
  OnInit,
} from '@angular/core';

import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';

import {TableColumnInterface} from '../dummy-table';
import {CarModel} from "../../pages/nomenclatures/car-models/car-model";
import {EditFormComponent} from "./edit-form";

@Component({
  selector: 'vfm-reference-list',
  templateUrl: './reference-list.component.html',
  styleUrls: ['./reference-list.component.scss']
})

export class ReferenceListComponent implements OnInit {
  public carId!: number;
  public items: any;
  public allTableColumns: TableColumnInterface[] = [];
  public dataType!: string
  public currentItem:any
  public svc: any
  constructor(
    private modal: NzModalRef,
    private message: NzMessageService,
    private modalService: NzModalService,
  )
  {
    this.carId = this.modal['config'].nzData.carId || 1;
    this.dataType = this.modal['config'].nzData.type;
    this.svc=this.modal['config'].nzData.service;
    this.allTableColumns=this.modal['config'].nzData.columns
    this.items=this.modal['config'].nzData.data;
  }

  ngOnInit(): void {
  }

  editItem(item?: any) {
    let title;
    if (item) {
      title = `${this.dataType}: ${item.name}`;
    } else {
      title = `New ${this.dataType}`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: EditFormComponent,
      nzWidth: '40vw',
      nzStyle: { top: '0' },
      nzData: {
        currentItem: item,
        type:this.dataType,
        service:this.svc
      },
      nzFooter: null,
    });
    modal.afterClose.subscribe((res) => {
      if (res) {
        this.message.info('Функцията не е имплементирана!');
      }
    });
  }

  removeItem() {
    this.modalService.confirm({
      nzTitle: `Are you sure you want to delete this item?`,
      nzOkText: `Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.delete(this.carId).subscribe({
          next: () => {
            this.message.create('success', `Item successfully deleted.`);
            this.svc.fetchLatest();
          },
          error: (error:any) => {
            if (error.status === 404) {
              this.message.error(`Item not found`);
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

  sortByDate(arr: any) {
    arr.sort(function (a: any, b: any) {
      return Number(new Date(b.fromDate)) - Number(new Date(a.fromDate));
    });
    return arr;
  }

  currentItemSelect(item: any) {
    if (item.id===this.currentItem?.id){
      this.currentItem=null
    }else {
      this.currentItem = item;
    }
  }

  saveNotes() {

  }
}
