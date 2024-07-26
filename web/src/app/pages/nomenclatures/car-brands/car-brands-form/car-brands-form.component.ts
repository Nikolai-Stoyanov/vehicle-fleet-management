import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import {CarBrandsService} from "../car-brands.service";

@Component({
  selector: 'vfm-users-form',
  templateUrl: './car-brands-form.component.html',
  styleUrls: ['./car-brands-form.component.scss']
})
export class CarBrandsFormComponent implements OnInit {
  public form!: FormGroup;
  @Input() public currentItem: any;

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
      id: this.fb.control({
        value: this.currentItem?.id || null,
        disabled: true
      }),
      name: this.fb.control(
        this.currentItem?.name || null,
        Validators.required
      ),
      description: this.fb.control(
        this.currentItem?.description || null,
      ),
      company: this.fb.control(
        this.currentItem?.company || null,
        Validators.required
      ),
      models: this.fb.control(
        {
          value: this.currentItem?.models || null,
          disabled: true
        }
      ),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }
    if (!this.currentItem?.id) {
      this.brandService.createBrand(this.form.getRawValue()).subscribe({
        next: () => {
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.statusText);
        }
      })
    }else {
      this.brandService.updateBrand(this.currentItem?.id,this.form.getRawValue()).subscribe({
        next: () => {
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.statusText);
        }
      })
    }

  }
}
