import { Component, OnInit } from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { CarBrandsFormComponent } from '../car-brands-form';
import { CarBrandsService } from '../car-brands.service';
import {CarBrand} from "../car-brands";

import { TableColumnInterface } from '../../../../shared/dummy-table';

@Component({
  selector: 'vfm-users-list',
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
    modal.afterClose.subscribe(() => {
        this.svc.fetchLatest().subscribe((res) => {
          this.currentItems = res;
        });
    });
  }

  removeBrand() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this brand?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deleteBrand(this.currentItem?.id).subscribe({
          next: (res) => {
            this.message.success(res.message);
            this.svc.fetchLatest().subscribe((res) => {
              this.currentItems = res;
            });
          },
          error: (error) => {
            this.message.error(error.status + ' ' + error.error.message);
          }
        });
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
}
