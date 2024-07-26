import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {CarBrandsService} from "../../car-brands/car-brands.service";
import {DateTimeForBackendPipe, DateTimePipe} from "../../../../shared/formatters";
import {CarModelsService} from "../car-models.service";

@Component({
  selector: 'vfm-users-form',
  templateUrl: './car-models-form.component.html',
  styleUrls: ['./car-models-form.component.scss'],
})
export class CarModelsFormComponent implements OnInit {
  public form!: FormGroup;
  @Input() public currentItem: any;
  public currentItemId: any;
  brandOptions: any[] = [];

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    protected modal: NzModalRef,
    private brandService: CarBrandsService,
    private modelService: CarModelsService,
    private dateTimePipe: DateTimePipe,
    private dateForBackendPipe:DateTimeForBackendPipe
  ) {
    this.currentItemId = this.modal['config'].nzData.id;
  }

  ngOnInit(): void {
    this.brandService.fetchLatest().subscribe((res) => res.forEach((item: any) => {
      this.brandOptions.push({label: item.name, value: item})
    }))
    if (this.currentItemId) {
      this.modelService.fetchModelById(this.currentItemId).subscribe((model: any) => {
        this.currentItem = model;
        this.getForm()
      })
    } else {
      this.getForm()
    }
  }


  getForm() {
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
      brand: this.fb.control(
        this.currentItem?.brand || null,
        Validators.required
      ),
      year: this.fb.control(
        this.dateTimePipe.transform(this.currentItem?.year) || null,
        Validators.required
      ),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }
    const formObject = {
      id: this.currentItemId,
      name: this.form.getRawValue().name,
      description: this.form.getRawValue().description,
      year: this.dateForBackendPipe.transform(this.form.getRawValue().year),
      brand: this.form.getRawValue().brand.id,
      status: this.form.getRawValue().status,
    }
    if (!this.currentItem?.id) {
      this.modelService.createModel(formObject).subscribe({
        next: () => {
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      })
    } else {
      this.modelService.updateModel(this.currentItem?.id, formObject).subscribe({
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
