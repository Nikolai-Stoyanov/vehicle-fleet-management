import { Component, OnInit } from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { CarBrandsFormComponent } from '../car-brands-form';
import { CarBrandsService } from '../car-brands.service';
import {CarBrand} from "../car-brands";

import { TableColumnInterface } from '../../../../shared/dummy-table';

@Component({
  selector: 'ap-car-models-list',
  templateUrl: './car-brands-list.component.html',
  styleUrls: ['./car-brands-list.component.scss'],
})
export class CarBrandsListComponent implements OnInit {

  public currentItems: CarBrand[] | undefined;
  public currentItem:any;
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;

  constructor(
    private svc: CarBrandsService,
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

  editBrand(item?: CarBrand) {
    let title;
    if (item) {
      title = `Brand: ${item.name}`;
    } else {
      title = `New brand`;
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
    modal.afterClose.subscribe((res) => {
      if (res) {
        this.message.info('Функцията не е имплементирана!');
      }
    });
  }

  removeBrand() {
    this.modalService.confirm({
      nzTitle: `Are you sure you want to delete this brand?`,
      nzOkText: `Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deleteBranch(this.currentItem?.id).subscribe({
          next: () => {
            this.message.create('success', `Brand successfully deleted.`);
            this.svc.fetchLatest();
          },
          error: (error) => {
            if (error.status === 404) {
              this.message.error(`Brand not found`);
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

  currentItemSelect(item: any) {
    if (item.id===this.currentItem?.id){
      this.currentItem=null
    }else {
      this.currentItem = item;
    }
  }
}
