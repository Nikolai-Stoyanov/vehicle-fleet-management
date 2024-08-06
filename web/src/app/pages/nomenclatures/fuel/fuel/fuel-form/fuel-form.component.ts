import {Component, OnDestroy, OnInit} from '@angular/core';
import {  FormBuilder, FormGroup, Validators } from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import { FuelType} from "../../fuel";
import {FuelService} from "../../fuel.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-fuel-form',
  templateUrl: './fuel-form.component.html',
  styleUrls: ['./fuel-form.component.scss']
})
export class FuelFormComponent implements OnInit, OnDestroy {
  public form!: FormGroup;
  public currentItem: any;
  public currentItemId:any;
  public subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private modal: NzModalRef,
    private message: NzMessageService,
    private svc: FuelService,
  ) {
    this.currentItemId = this.modal['config'].nzData.id;
  }

  ngOnInit(): void {
    if (this.currentItemId) {
      this.subscriptions.push(  this.svc.fetchFuelById(this.currentItemId).subscribe((res:FuelType) => {
        this.currentItem = res;
        this.getForm();
      }));
    }else {
      this.getForm();
    }
  }

  getForm(): void {
    this.form = this.fb.group({
      id: this.fb.control({value: this.currentItem?.id || null, disabled: true}),
      name: this.fb.control(this.currentItem?.name || null, Validators.required),
      description: this.fb.control(this.currentItem?.description || null),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }

    const formObject={
      name:this.form.getRawValue().name,
      id:this.form.getRawValue().id,
      description:this.form.getRawValue().description,
      status:this.form.getRawValue().status,
    }

    if (!this.currentItem?.id) {
      this.subscriptions.push( this.svc.createFuel(formObject).subscribe({
        next: (res) => {
          this.message.success(res.message);
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      }));
    }else {
      this.subscriptions.push( this.svc.updateFuel(this.currentItem?.id,formObject).subscribe({
        next: (res) => {
          this.message.success(res.message);
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      }));
    }
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
