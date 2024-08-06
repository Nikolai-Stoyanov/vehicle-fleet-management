import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import {CarBrandsService} from "../car-brands.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-car-persons-form',
  templateUrl: './car-brands-form.component.html',
  styleUrls: ['./car-brands-form.component.scss']
})
export class CarBrandsFormComponent implements OnInit, OnDestroy  {
  public form!: FormGroup;
  @Input() public currentItem: any;
  public subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    protected modal: NzModalRef,
    private brandService: CarBrandsService,
  ) {
    this.currentItem = this.modal['config'].nzData.currentItem;
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: this.fb.control({value: this.currentItem?.id || null, disabled: true}),
      name: this.fb.control(this.currentItem?.name || null, Validators.required),
      description: this.fb.control(this.currentItem?.description || null,),
      company: this.fb.control(this.currentItem?.company || null, Validators.required),
      models: this.fb.control({value: this.currentItem?.models || null, disabled: true}),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }
    if (!this.currentItem?.id) {
      this.subscriptions.push( this.brandService.createBrand(this.form.getRawValue()).subscribe({
        next: (res) => {
          this.message.success(res.message);
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      }));
    }else {
      this.subscriptions.push(this.brandService.updateBrand(this.currentItem?.id,this.form.getRawValue()).subscribe({
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
