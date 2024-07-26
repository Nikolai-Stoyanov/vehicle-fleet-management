import { Component, OnInit } from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { DeclarationsFormComponent } from '../declarations-form';
import { DeclarationsService } from '../declarations.service';
import {DeclarationList} from "../declarations";

import { TableColumnInterface } from '../../../shared/dummy-table';
import {RegistrationNumberService} from "../../../shared/services";

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
  currentRegistrationNumber: any;
  registrationNumberOptions: any[]=[];

  constructor(
    private svc: DeclarationsService,
    private message: NzMessageService,
    private modalService: NzModalService,
    private registrationNumberService: RegistrationNumberService,
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
      title = $localize`New declaration (${this.currentRegistrationNumber.registrationNumber}) `;
    }
    this.createModalVisible=false
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: DeclarationsFormComponent,
      nzWidth: '60vw',
      nzStyle: { top: '0' },
      nzData: {
        currentId: item?.id,
        registrationNumber: this.currentRegistrationNumber,
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

  createModal() {
      this.registrationNumberService.fetchAllNumber().subscribe((res:any)=>{
        res.forEach((item:any )=> {
          this.registrationNumberOptions.push({
            label:item.registrationNumber,value:item
          })
          this.createModalVisible=true
        })

      })
  }

  closeModal() {
    this.createModalVisible=false
    this.currentRegistrationNumber=null;
  }
}
