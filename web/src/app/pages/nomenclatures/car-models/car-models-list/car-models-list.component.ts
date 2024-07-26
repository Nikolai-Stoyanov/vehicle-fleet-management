import { Component, OnInit } from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { CarModelsFormComponent } from '../car-models-form';
import { CarModelsService } from '../car-models.service';
import { CarModel} from "../car-model";

import { TableColumnInterface } from '../../../../shared/dummy-table';

@Component({
  selector: 'vfm-users-list',
  templateUrl: './car-models-list.component.html',
  styleUrls: ['./car-models-list.component.scss'],
})
export class CarModelsListComponent implements OnInit {

  public currentItems: CarModel[] | undefined;
  public currentItem:any;
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;

  constructor(
    private svc: CarModelsService,
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
    modal.afterClose.subscribe((res) => {
      this.svc.fetchLatest().subscribe((res) => {
        this.currentItems = res;
      });
    });
  }

  removeModel() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this model?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deleteModel(this.currentItem?.id).subscribe({
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

  currentItemSelect(item: CarModel) {
    if (item.id===this.currentItem?.id){
      this.currentItem=null
    }else {
      this.currentItem = item;
    }
  }
}
