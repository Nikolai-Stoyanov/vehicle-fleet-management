import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {UsersService} from "../users.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-users-form',
  templateUrl: './users-form.component.html',
  styleUrls: ['./users-form.component.scss'],
})
export class UsersFormComponent implements OnInit, OnDestroy {
  public form!: FormGroup;
  @Input() public currentItemId: any;
  roleOptions: any[]=[];
  public subscriptions: Subscription[] = []

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    protected modal: NzModalRef,
    private userService:UsersService,
  ) {
    this.currentItemId = this.modal['config'].nzData.currentItem;
  }

  ngOnInit(): void {
    this.subscriptions.push(this.userService.userRoleListData().subscribe((res:any)=>{
    res.forEach((item:any)=>{
        this.roleOptions.push({label: item.role, value: item})
      })
    }));
    this.subscriptions.push(this.userService.fetchUserById(this.currentItemId).subscribe((res:any)=>{
      this.form = this.fb.group({
        id: this.fb.control({value: res?.id || null, disabled: true}),
        username: this.fb.control(res?.username || null, Validators.required),
        email: this.fb.control(res?.email || null, Validators.required),
        roles: this.fb.control(res?.roles || [])
      });
    }));
  }


  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }
    this.subscriptions.push( this.userService.updateUser(this.currentItemId,this.form.getRawValue()).subscribe({
      next: (res) => {
        this.message.success(res.message);
        this.modal.destroy();
      },
      error: (error: any) => {
        this.message.error(error.status + ' ' + error.error.message);
      }
    }));
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
