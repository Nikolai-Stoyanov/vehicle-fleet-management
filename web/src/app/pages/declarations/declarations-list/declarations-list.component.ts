import { Component, OnInit } from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { DeclarationsFormComponent } from '../declarations-form';
import { DeclarationsService } from '../declarations.service';
import {DeclarationList} from "../declarations";

import { TableColumnInterface } from '../../../shared/dummy-table';

@Component({
  selector: 'vfm-declarations-list',
  templateUrl: './declarations-list.component.html',
  styleUrls: ['./declarations-list.component.scss'],
})
export class DeclarationsListComponent implements OnInit {

  public currentItems: DeclarationList[] | undefined;
  public currentItem:any;
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;
  createModalVisible: boolean=false;

  constructor(
    private svc: DeclarationsService,
    private message: NzMessageService,
    private modalService: NzModalService,
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

  editDeclaration(item?: DeclarationList) {
    let title;
    if (item) {
      title = $localize`Edit declaration for: ${item.registrationNumber}`;
    } else {
      title = $localize`New declaration`;
    }
    this.createModalVisible=false
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: DeclarationsFormComponent,
      nzWidth: '60vw',
      nzStyle: { top: '0' },
      nzData: {
        currentId: item?.id,
      },
      nzFooter: null,
    });
    modal.afterClose.subscribe((res) => {
      if (res) {
        this.message.info($localize`Функцията не е имплементирана!`);
      }
    });
  }

  removeDeclaration() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this declaration?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deleteDeclaration(this.currentItem?.id).subscribe({
          next: () => {
            this.message.create('success', $localize`Declaration successfully deleted.`);
            this.svc.fetchLatest();
          },
          error: (error) => {
            if (error.status === 404) {
              this.message.error($localize`Declaration not found`);
            } else {
              this.message.error(error);
            }
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
