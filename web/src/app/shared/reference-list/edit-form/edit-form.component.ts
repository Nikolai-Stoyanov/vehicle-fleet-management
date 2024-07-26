import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';

import {DateTimePipe} from "../../formatters";
import {EditFormTypes} from "../../shared";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-edit-form',
  templateUrl: './edit-form.component.html',
  styleUrls: ['./edit-form.component.scss'],
})
export class EditFormComponent implements OnInit, OnDestroy {
  public form!: FormGroup;
  @Input() public currentItem: any;
  protected readonly EditFormTypes = EditFormTypes;
  public type!: string
  public svc: any
  public subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    protected modal: NzModalRef,
    private dateTimePipe: DateTimePipe
  ) {
    this.currentItem = this.modal['config'].nzData.currentItem;
    this.type = this.modal['config'].nzData.type;
    this.svc = this.modal['config'].nzData.service;
  }

  ngOnInit(): void {

    this.form = this.fb.group({
      id: this.fb.control({
        value: this.currentItem?.id || null,
        disabled: true
      }),
      vignetteType: this.fb.control(
        this.currentItem?.vignetteType || null
      ),
      description: this.fb.control(
        this.currentItem?.description || null
      ),
      dateFrom: this.fb.control(
        this.dateTimePipe.transform(this.currentItem?.dateFrom) || null
      ),
      dateTo: this.fb.control(
        this.dateTimePipe.transform(this.currentItem?.dateTo) || null
      ),
      driverId: this.fb.control(
        this.currentItem?.driverId || null
      ),
      phoneNumber: this.fb.control(
        this.currentItem?.phoneNumber || null
      ),
      registration: this.fb.control(
        this.currentItem?.registration || null
      ),
      registrationDate: this.fb.control(
        this.currentItem?.registrationDate || null
      ),
      cardNumber: this.fb.control(
        this.currentItem?.cardNumber || null
      ),
      cardLimit: this.fb.control(
        this.currentItem?.cardLimit || null
      ),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }

  onSubmit() {
    if (this.form.getRawValue().id) {
      const sub1 = this.svc.update(this.form.getRawValue().id, this.form.getRawValue()).subscribe({
        next: () => {
          this.modal.destroy();
        },
        error: () => {
        }
      });
      this.subscriptions.push(sub1);
    } else {
      const sub2 = this.svc.create(this.form.getRawValue()).subscribe({
        next: () => {
          this.modal.destroy();
        },
        error: () => {
        }
      });
      this.subscriptions.push(sub2);
    }
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
