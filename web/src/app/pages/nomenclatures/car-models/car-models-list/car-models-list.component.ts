import {Component, OnDestroy, OnInit} from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { CarModelsFormComponent } from '../car-models-form';
import { CarModelsService } from '../car-models.service';
import { CarModel} from "../car-model";

import { TableColumnInterface } from '../../../../shared/dummy-table';
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-car-persons-list',
  templateUrl: './car-models-list.component.html',
  styleUrls: ['./car-models-list.component.scss'],
})
export class CarModelsListComponent implements OnInit, OnDestroy  {

  public currentItems: CarModel[] | undefined;
  public currentItem:any;
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;
  public subscriptions: Subscription[] = [];

  constructor(
    private svc: CarModelsService,
    private message: NzMessageService,
    private modalService: NzModalService
  ) {}

  ngOnInit() {
    this.subscriptions.push(this.svc.getColumns().subscribe((res) => {
      this.allTableColumns = res;

    }));

    this.loading = true;
    this.subscriptions.push(this.svc.fetchLatest().subscribe((res) => {
      this.currentItems = res;
      this.loading = false;
    }));
  }

  editModel(item?: CarModel) {
    let title;
    if (item) {
      title = $localize`Model: ${item.name}`;
    } else {
      title = $localize`New Model`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: CarModelsFormComponent,
      nzWidth: '40vw',
      nzStyle: { top: '0' },
      nzData: {
        id: item?.id,
      },
      nzFooter: null,
    });
    this.subscriptions.push(modal.afterClose.subscribe(() => {
      this.subscriptions.push(this.svc.fetchLatest().subscribe((res) => {
        this.currentItems = res;
      }));
    }));
  }

  removeModel() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this model?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        this.subscriptions.push(this.svc.deleteModel(this.currentItem?.id).subscribe({
          next: (res) => {
            this.message.success(res.message);
            this.subscriptions.push(this.svc.fetchLatest().subscribe((res) => {
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

  currentItemSelect(item: CarModel) {
    if (item.id===this.currentItem?.id){
      this.currentItem=null
    }else {
      this.currentItem = item;
    }
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
