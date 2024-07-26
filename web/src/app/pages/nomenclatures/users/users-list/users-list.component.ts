import { Component, OnInit } from '@angular/core';

import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

import { UsersFormComponent } from '../users-form';
import { UsersService } from '../users.service';
import { Users} from "../users";

import { TableColumnInterface } from '../../../../shared/dummy-table';

@Component({
  selector: 'vfm-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss'],
})
export class UsersListComponent implements OnInit {

  public currentItems: Users[] | undefined;
  public currentItem:any;
  public allTableColumns: TableColumnInterface[] = [];
  public loading = false;

  constructor(
    private svc: UsersService,
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

  editUser(item?: Users) {
    let title;
    if (item) {
      title = $localize`User: ${item.username}`;
    }

    const modal = this.modalService.create({
      nzTitle: title,
      nzContent: UsersFormComponent,
      nzWidth: '40vw',
      nzStyle: { top: '0' },
      nzData: {
        currentItem: item?.id,
      },
      nzFooter: null,
    });
    modal.afterClose.subscribe(() => {
      this.svc.fetchLatest().subscribe((res) => {
        this.currentItems = res;
      });
    });
  }

  removeUser() {
    this.modalService.confirm({
      nzTitle: $localize`Are you sure you want to delete this user?`,
      nzOkText: $localize`Yes`,
      nzOkDanger: true,

      nzOnOk: () => {
        const sub2 = this.svc.deleteUser(this.currentItem?.id).subscribe({
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

  currentItemSelect(item: Users) {
    if (item.id===this.currentItem?.id){
      this.currentItem=null
    }else {
      this.currentItem = item;
    }
  }
}
