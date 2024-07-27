import { Component, OnInit } from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { TableColumnInterface } from '../../../../../shared/dummy-table';

import { FuelFormComponent } from '../fuel-form';
import { FuelService } from '../../fuel.service';

@Component({
  selector: 'vfm-fuel-list',
  templateUrl: './fuel-list.component.html',
  styleUrls: ['./fuel-list.component.scss'],
})
export class FuelListComponent implements OnInit {
  public currentItems: any;
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;
  currentItem: any;

  constructor(
    private svc: FuelService,
    private message: NzMessageService,
    private modalService: NzModalService
  ) {}

  ngOnInit() {
    this.svc.getFuelColumns().subscribe((res) => {
      this.allTableColumns = res;
    });

    this.loading = true;
    this.svc.fetchLatestFuels().subscribe((res) => {
      this.currentItems = res;
      this.loading = false;
    });
  }

  editFuel(item?: any) {
    let title;
    if (item) {
      title = $localize`Fuel: ${item.name}`;
    } else {
      title = $localize`New fuel`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: FuelFormComponent,
      nzWidth: '40vw',
      nzStyle: { top: '0' },
      nzData: {
        id: item?.id,
      },
      nzFooter: null,
    });
    modal.afterClose.subscribe((res) => {
      this.svc.fetchLatestFuels().subscribe((res) => {
        this.currentItems = res;
      });
    });
  }

  currentItemSelect(item: any) {

    if (item.id===this.currentItem?.id){
      this.currentItem=null
    }else {
      this.currentItem = item;
    }
  }

  removeFuel() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this fuel?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deleteFuel(this.currentItem?.id).subscribe({
          next: (res) => {
            this.message.success(res.message);
            this.svc.fetchLatestFuels().subscribe((res) => {
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
}
