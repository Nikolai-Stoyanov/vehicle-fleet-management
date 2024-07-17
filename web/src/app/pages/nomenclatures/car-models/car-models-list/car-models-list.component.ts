import { Component, OnInit } from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { CarModelsFormComponent } from '../car-models-form';
import { CarModelsService } from '../car-models.service';
import { CarModel} from "../car-model";

import { TableColumnInterface } from '../../../../shared/dummy-table';

@Component({
  selector: 'vfm-car-models-list',
  templateUrl: './car-models-list.component.html',
  styleUrls: ['./car-models-list.component.scss'],
})
export class CarModelsListComponent implements OnInit {

  public currentItems: CarModel[] | undefined;
  public currentItem:any;
  public allTableColumns: TableColumnInterface[] = [];
  public currentTableColumns: TableColumnInterface[] = [];
  public loading = false;

  constructor(
    private svc: CarModelsService,
    private message: NzMessageService,
    private modalService: NzModalService
  ) {}

  ngOnInit() {
    this.svc.getColumns().subscribe((res) => {
      this.allTableColumns = res;
      this.allTableColumns.forEach((column: TableColumnInterface) => {
        if (column.visible === true) {
          this.currentTableColumns.push(column);
        }
      });
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
      title = `Model: ${item.name}`;
    } else {
      title = `New Model`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: CarModelsFormComponent,
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

  removeModel() {
    this.modalService.confirm({
      nzTitle: `Are you sure you want to delete this model?`,
      nzOkText: `Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deleteModel(this.currentItem?.id).subscribe({
          next: () => {
            this.message.create('success', `Model successfully deleted.`);
            this.svc.fetchLatest();
          },
          error: (error) => {
            if (error.status === 404) {
              this.message.error(`Model not found`);
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

  currentItemSelect(item: CarModel) {
    if (item.id===this.currentItem?.id){
      this.currentItem=null
    }else {
      this.currentItem = item;
    }
  }
}
