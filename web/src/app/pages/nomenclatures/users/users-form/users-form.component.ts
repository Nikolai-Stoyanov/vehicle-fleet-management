import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {UsersService} from "../users.service";

@Component({
  selector: 'vfm-users-form',
  templateUrl: './users-form.component.html',
  styleUrls: ['./users-form.component.scss'],
})
export class UsersFormComponent implements OnInit {
  public form!: FormGroup;
  @Input() public currentItemId: any;
  roleOptions: any[]=[];

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    protected modal: NzModalRef,
    private userService:UsersService,
  ) {
    this.currentItemId = this.modal['config'].nzData.currentItem;
  }

  ngOnInit(): void {
    this.userService.userRoleListData().subscribe((res:any)=>{
    res.forEach((item:any)=>{
        this.roleOptions.push({label: item.role, value: item})
      })
    })
    this.userService.fetchUserById(this.currentItemId).subscribe((res:any)=>{
      this.form = this.fb.group({
        id: this.fb.control({value: res?.id || null, disabled: true}),
        username: this.fb.control(res?.username || null, Validators.required),
        email: this.fb.control(res?.email || null, Validators.required),
        roles: this.fb.control(res?.roles || [])
      });
    })
  }


  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }
    this.userService.updateUser(this.currentItemId,this.form.getRawValue()).subscribe({
      next: (res) => {
        console.log(res)
        this.modal.destroy();
      },
      error: (error: any) => {
        console.log(error);
        this.message.error(error.status + ' ' + error.error.message);
      }
    })
  }
}
