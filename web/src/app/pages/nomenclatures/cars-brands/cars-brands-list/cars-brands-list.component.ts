import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { NzMessageService } from 'ng-zorro-antd/message';

import { TableColumnInterface } from '../../../../shared/dummy-table';
import { NzModalService } from 'ng-zorro-antd/modal';

import { CarsBrandsFormComponent } from '../cars-brands-form';
import { CarBrandsService } from '../car-brands.service';

@Component({
  selector: 'ap-cars-brands-list',
  templateUrl: './cars-brands-list.component.html',
  styleUrls: ['./cars-brands-list.component.scss'],
})
export class CarsBrandsListComponent implements OnInit {

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
    private svc: CarBrandsService,
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

  onEditClick(item?: any) {
    let title;
    if (item) {
      title = `Модел: ${item.model}`;
    } else {
      title = `Нов модел`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: CarsBrandsFormComponent,
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
}
