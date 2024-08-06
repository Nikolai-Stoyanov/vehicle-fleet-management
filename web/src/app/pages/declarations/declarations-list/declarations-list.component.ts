import {Component, OnDestroy, OnInit} from '@angular/core';

import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalService} from 'ng-zorro-antd/modal';

import {DeclarationsFormComponent} from '../declarations-form';
import {DeclarationsService} from '../declarations.service';
import {DeclarationList} from "../declarations";

import {TableColumnInterface} from '../../../shared/dummy-table';
import {CarRecordService} from "../../car-records/car-record.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-declarations-list',
  templateUrl: './declarations-list.component.html',
  styleUrls: ['./declarations-list.component.scss'],
})
export class DeclarationsListComponent implements OnInit, OnDestroy {

  public currentItems: DeclarationList[] | undefined;
  public currentItem: any;
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;
  createModalVisible: boolean = false;
  currentRegistrationNumber: any;
  registrationNumberOptions: any[] = [];
  public subscriptions: Subscription[] = [];

  constructor(
    private svc: DeclarationsService,
    private message: NzMessageService,
    private modalService: NzModalService,
    private carRecordService: CarRecordService,
  ) {
  }

  ngOnInit() {
    this.subscriptions.push(this.svc.getColumns().subscribe((res) => {
      this.allTableColumns = res;
    }));

    this.loading = true;
    this.subscriptions.push(this.svc.fetchLatestDeclarations().subscribe((res) => {
      this.currentItems = res;
      this.loading = false;
    }));
  }

  editDeclaration(item?: DeclarationList) {

    let title;
    if (item) {
      title = $localize`Edit declaration for: ${item.registrationNumber}`;
    } else {
      title = $localize`New declaration for ${this.currentRegistrationNumber.registrationNumber}`;
    }

    this.createModalVisible = false
    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: DeclarationsFormComponent,
      nzWidth: '60vw',
      nzStyle: {top: '0'},
      nzData: {
        currentId: item?.id,
        registrationNumberObject: this.currentRegistrationNumber
      },
      nzFooter: null,
    });
    this.closeModal()
    this.subscriptions.push(modal.afterClose.subscribe(() => {
      this.subscriptions.push(this.svc.fetchLatestDeclarations().subscribe((res: DeclarationList[]) => {
        this.currentItems = res;
      }));
    }));
  }

  removeDeclaration() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this declaration?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        this.subscriptions.push(this.svc.deleteDeclaration(this.currentItem?.id).subscribe({
          next: (res) => {
            this.message.success(res.message);
            this.subscriptions.push(this.svc.fetchLatestDeclarations().subscribe((res: DeclarationList[]) => {
              this.currentItems = res;
            }));
          },
          error: (error) => {
            this.message.error(error.status + ' ' + error.error.message);
          }
        }));
      },
      nzCancelText: $localize`No`,
      nzOnCancel: () => {
      }
    });
  }

  currentItemSelect(item: any) {
    if (item.id === this.currentItem?.id) {
      this.currentItem = null
    } else {
      this.currentItem = item;
    }
  }

  createModal() {
    this.subscriptions.push(this.carRecordService.fetchAllRegistrationNumbers().subscribe((res: any) => {
      res.forEach((item: any) => {
        this.registrationNumberOptions.push({
          label: item.registrationNumber, value: item
        })
        this.createModalVisible = true
      })
    }))
  }

  closeModal() {
    this.createModalVisible = false
    this.currentRegistrationNumber = null;
    this.registrationNumberOptions = []
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
