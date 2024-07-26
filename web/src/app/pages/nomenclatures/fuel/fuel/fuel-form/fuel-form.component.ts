import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {  FormBuilder, FormGroup, Validators } from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import {FuelProviderType, FuelType} from "../../fuel";
import {FuelService} from "../../fuel.service";

@Component({
  selector: 'vfm-fuel-form',
  templateUrl: './fuel-form.component.html',
  styleUrls: ['./fuel-form.component.scss']
})
export class FuelFormComponent implements OnInit {
  public form!: FormGroup;
  public currentItem: any;
  public currentItemId:any;

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
      this.svc.fetchFuelById(this.currentItemId).subscribe((res:FuelType) => {
        this.currentItem = res;
        console.log(res)
        this.getForm();
      })
    }else {
      this.getForm();
    }

  }

  getForm(): void {
    this.form = this.fb.group({
      id: this.fb.control({
        value: this.currentItem?.id || null,
        disabled: true
      }),
      name: this.fb.control(
        this.currentItem?.name || null,
        Validators.required
      ),
      description: this.fb.control(
        this.currentItem?.description || null
      ),
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
      this.svc.createFuel(formObject).subscribe({
        next: () => {
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      })
    }else {
      this.svc.updateFuel(this.currentItem?.id,formObject).subscribe({
        next: () => {
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      })
    }

  }
}
