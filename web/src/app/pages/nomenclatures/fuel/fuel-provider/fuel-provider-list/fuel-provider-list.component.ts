import {Component, OnDestroy, OnInit} from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import {FuelService} from "../../fuel.service";
import {TableColumnInterface} from "../../../../../shared/dummy-table";
import {FuelProviderType} from "../../fuel";
import {FuelProviderFormComponent} from "../fuel-provider-form";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-fuel-provider-list',
  templateUrl: './fuel-provider-list.component.html',
  styleUrls: ['./fuel-provider-list.component.scss'],
})
export class FuelProviderListComponent implements OnInit, OnDestroy {

  public currentItems: FuelProviderType[]=[];
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;
  public currentItem: any;
  public subscriptions: Subscription[] = [];

  constructor(
    private svc: FuelService,
    private message: NzMessageService,
    private modalService: NzModalService
  ) {}

  ngOnInit() {
    this.subscriptions.push(this.svc.getFuelProviderColumns().subscribe((res) => {
      this.allTableColumns = res;
    }));

    this.loading = true;
    this.subscriptions.push(this.svc.fetchLatestSuppliers().subscribe((res) => {
      this.currentItems = res;
      this.loading = false;
    }));
  }

  editFuelProvider(item?: FuelProviderType) {
    let title;
    if (item) {
      title = $localize`Provider: ${item.name}`;
    } else {
      title = $localize`New provider`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: FuelProviderFormComponent,
      nzWidth: '60vw',
      nzStyle: { top: '0' },
      nzData: {
        id: item?.id,
      },
      nzFooter: null,
    });
    this.subscriptions.push( modal.afterClose.subscribe(() => {
      this.subscriptions.push(this.svc.fetchLatestSuppliers().subscribe((res) => {
        this.currentItems = res;
      }));
    }));
  }

  currentItemSelect(item: FuelProviderType) {
    if (item.id===this.currentItem?.id){
      this.currentItem=null
    }else {
      this.currentItem = item;
    }
  }

  removeFuelProvider() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this provider?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        this.subscriptions.push(this.svc.deleteSupplier(this.currentItem?.id).subscribe({
          next: (res) => {
            this.message.success(res.message);
            this.subscriptions.push( this.svc.fetchLatestSuppliers().subscribe((res) => {
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

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
