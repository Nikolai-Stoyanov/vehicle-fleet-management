import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {CarBrandsService} from "../../car-brands/car-brands.service";
import {CarPersonsService} from "../car-persons.service";
import {CarPerson} from "../car-person";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-car-persons-form',
  templateUrl: './car-persons-form.component.html',
  styleUrls: ['./car-persons-form.component.scss'],
})
export class CarPersonsFormComponent implements OnInit, OnDestroy  {
  public form!: FormGroup;
  @Input() public currentItem: any;
  public currentItemId: any;
  brandOptions: any[] = [];
  public subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    protected modal: NzModalRef,
    private brandService: CarBrandsService,
    private modelService: CarPersonsService,
  ) {
    this.currentItemId = this.modal['config'].nzData.id;
  }

  ngOnInit(): void {
    this.subscriptions.push( this.brandService.fetchLatest().subscribe((res) => res.forEach((item: any) => {
      this.brandOptions.push({label: item.name, value: item})
    })));
    if (this.currentItemId) {
      this.subscriptions.push( this.modelService.fetchPersonById(this.currentItemId).subscribe((person: CarPerson) => {
        this.currentItem = person;
        this.getForm()
      }));
    } else {
      this.getForm()
    }
  }


  getForm() {
    this.form = this.fb.group({
      id: this.fb.control({value: this.currentItem?.id || null, disabled: true}),
      firstName: this.fb.control(this.currentItem?.firstName || null, Validators.required),
      lastName: this.fb.control(this.currentItem?.lastName || null, Validators.required),
      phoneNumber: this.fb.control(this.currentItem?.phoneNumber || null, Validators.required),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }

    if (!this.currentItemId) {
      this.subscriptions.push( this.modelService.createPerson(this.form.getRawValue()).subscribe({
        next: (res) => {
          this.message.success(res.message);
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      }));
    } else {
      this.subscriptions.push( this.modelService.updatePerson(this.currentItemId, this.form.getRawValue()).subscribe({
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
