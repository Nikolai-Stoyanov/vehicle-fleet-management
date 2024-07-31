import { Component, OnInit } from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { CarPersonsFormComponent } from '../car-persons-form';
import { CarPersonsService } from '../car-persons.service';
import {CarPerson} from "../car-person";

import { TableColumnInterface } from '../../../../shared/dummy-table';

@Component({
  selector: 'vfm-car-persons-list',
  templateUrl: './car-persons-list.component.html',
  styleUrls: ['./car-persons-list.component.scss'],
})
export class CarPersonsListComponent implements OnInit {

  public currentItems: CarPerson[] | undefined;
  public currentItem:any;
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;

  constructor(
    private svc: CarPersonsService,
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

  editPerson(item?: CarPerson) {
    let title;
    if (item) {
      title = $localize`Person: ${item.fullName}`;
    } else {
      title = $localize`New person`;
    }
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: CarPersonsFormComponent,
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

  removePerson() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this person?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deletePerson(this.currentItem?.id).subscribe({
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

  currentItemSelect(item: CarPerson) {
    if (item.id===this.currentItem?.id){
      this.currentItem=null
    }else {
      this.currentItem = item;
    }
  }
}
