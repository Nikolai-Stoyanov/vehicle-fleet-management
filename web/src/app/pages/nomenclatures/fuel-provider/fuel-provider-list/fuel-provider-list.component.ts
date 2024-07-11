import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { NzMessageService } from 'ng-zorro-antd/message';

import { PaginationSettings } from '@shared/shared';
import { TableColumnInterface } from '@libs/dummy-table/src/lib';
import { NzModalService } from 'ng-zorro-antd/modal';

import { FuelProviderFormComponent } from '../fuel-provider-form';
import { CarTypesService } from '@shared/car-types';
import { FuelProviderTypeService } from '@shared/fuel-provider-type';

@Component({
  selector: 'ap-fuel-provider-list',
  templateUrl: './fuel-provider-list.component.html',
  styleUrls: ['./fuel-provider-list.component.scss'],
})
export class FuelProviderListComponent implements OnInit {
  public paginationSettings!: PaginationSettings;
  public currentItems: any;
  public allTableColumns: TableColumnInterface[] = [];
  public currentTableColumns: TableColumnInterface[] = [];
  public tableColumn: any;
  public isTableVisible: boolean = false;
  public rangePicker: any;
  public loading = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private svc: FuelProviderTypeService,
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
    this.svc.fetchAll().subscribe((res) => {
      this.currentItems = res;
      this.loading = false;
    });
  }

  getFuelOptionsAsString(currentItems: any): string {
    let result: string = '';
    currentItems.forEach((item: any) => {
      result += `${item.name}; `;
    });
    return result;
  }

  onEditClick(item?: any) {
    let title;
    if (item) {
      title = `Доставчик: < ${item.name} >`;
    } else {
      title = `Нов доставчик`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: FuelProviderFormComponent,
      nzWidth: '60vw',
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
}
