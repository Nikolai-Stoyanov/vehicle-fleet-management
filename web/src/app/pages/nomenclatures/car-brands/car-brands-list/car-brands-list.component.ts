import {Component, OnDestroy, OnInit} from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { CarBrandsFormComponent } from '../car-brands-form';
import { CarBrandsService } from '../car-brands.service';
import {CarBrand} from "../car-brands";

import { TableColumnInterface } from '../../../../shared/dummy-table';
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-car-persons-list',
  templateUrl: './car-brands-list.component.html',
  styleUrls: ['./car-brands-list.component.scss'],
})
export class CarBrandsListComponent implements OnInit, OnDestroy {

  public currentItems: CarBrand[] | undefined;
  public currentItem:any;
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;
  public subscriptions: Subscription[] = [];

  constructor(
    private svc: CarBrandsService,
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

  editBrand(item?: CarBrand) {
    let title;
    if (item) {
      title = $localize`Brand: ${item.name}`;
    } else {
      title = $localize`New brand`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: CarBrandsFormComponent,
      nzWidth: '40vw',
      nzStyle: { top: '0' },
      nzData: {
        currentItem: item,
      },
      nzFooter: null,
    });
    this.subscriptions.push(modal.afterClose.subscribe(() => {
      this.subscriptions.push(this.svc.fetchLatest().subscribe((res) => {
          this.currentItems = res;
        }));
    }));
  }

  removeBrand() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this brand?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        this.subscriptions.push(this.svc.deleteBrand(this.currentItem?.id).subscribe({
          next: (res) => {
            this.message.success(res.message);
            this.subscriptions.push( this.svc.fetchLatest().subscribe((res) => {
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

  currentItemSelect(item: any) {
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
