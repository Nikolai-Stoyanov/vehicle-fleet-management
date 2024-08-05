import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import {CarRecordService} from "../../car-records/car-record.service";
import {DeclarationsService} from "../declarations.service";
import {FuelService} from "../../nomenclatures/fuel/fuel.service";
import {FuelProviderType, FuelType} from "../../nomenclatures/fuel/fuel";
import {DateTimeForBackendPipe, DateTimePipe} from "../../../shared/formatters";
import {format} from "date-fns";
import {NzMessageService} from "ng-zorro-antd/message";
import {Declaration} from "../declarations";
import {Subscription} from "rxjs";

@Component({
  selector: 'ap-declarations-form',
  templateUrl: './declarations-form.component.html',
  styleUrls: ['./declarations-form.component.scss'],
})
export class DeclarationsFormComponent implements OnInit, OnDestroy {
  public form!: FormGroup;
  public currentItem!: Declaration;
  public currentItemId: any;
  public registrationNumberObject: any
  public fuelKindOptions: any = [];
  public fuelSupplierOptions: any = [];
  public subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private modal: NzModalRef,
    private message: NzMessageService,
    private svc: DeclarationsService,
    private fuelService: FuelService,
    private dateForBackendPipe: DateTimeForBackendPipe,
    private dateTimePipe: DateTimePipe,
    private carRecordService: CarRecordService,
  ) {
    this.currentItemId = this.modal['config'].nzData.currentId;
    this.registrationNumberObject = this.modal['config'].nzData.registrationNumberObject;
  }

  ngOnInit(): void {
    this.getOptions()
    if (this.currentItemId) {
      this.subscriptions.push(this.svc.fetchDeclarationById(this.currentItemId).subscribe((res: any) => {
        this.currentItem = res;
        this.getForm()
      }));
    } else {
      this.subscriptions.push(this.carRecordService.getCarRecordInfo(this.registrationNumberObject.id).subscribe((res: any) => {
        this.currentItem = res
        this.getForm()
      }));
    }
  }

  private getForm() {
    this.form = this.fb.group({
      id: this.fb.control({value: this.currentItem?.id || null, disabled: true}),
      date: this.fb.control(this.currentItem?.date || null, Validators.required),
      period: this.fb.control(
        this.currentItem?.period || null, Validators.required),
      lastMileage: this.fb.control(
        {value: this.currentItem?.lastMileage || null, disabled: true}, Validators.required),
      responsible: this.fb.control(
        {value: this.currentItem?.responsible || null, disabled: true}, Validators.required),
      newMileage: this.fb.control(
        this.currentItem?.newMileage || null, Validators.required),
      registrationNumber: this.fb.control(
        {value: this.currentItem?.registrationNumber || null, disabled: true}, Validators.required),
      mileage: this.fb.control(
        this.currentItem?.mileage || null, Validators.required),
      fuelType: this.fb.control({value: this.currentItem?.fuelType || null, disabled: true}, Validators.required),
      fuelKind: this.fb.control(this.currentItem?.fuelKind || null, Validators.required),
      fuelSupplier: this.fb.control(this.currentItem?.fuelSupplier || null, Validators.required),
      fuelAmount: this.fb.control(this.currentItem?.fuelAmount || null, Validators.required),
      fuelPrice: this.fb.control(this.currentItem?.fuelPrice || null,),
      createdBy: this.fb.control(
        {value: this.currentItem?.createdBy || null, disabled: true}, Validators.required),
      createdAt: this.fb.control(
        {value: this.dateTimePipe.transform(this.currentItem?.createdAt) || null, disabled: true}, Validators.required),
      updatedBy: this.fb.control(
        {value: this.currentItem?.updatedBy || null, disabled: true}, Validators.required),
      updatedAt: this.fb.control(
        {value: this.dateTimePipe.transform(this.currentItem?.updatedAt) || null, disabled: true}, Validators.required),
    });
    this.form.get('mileage')?.setValue(Math.abs(Number(this.form.getRawValue().newMileage)) - Number(this.form.getRawValue().lastMileage));

    this.form.get('newMileage')?.valueChanges.subscribe(res => {
      this.form.get('mileage')?.setValue(Math.abs(Number(res) - Number(this.form.getRawValue().lastMileage)))
    })
  }


  public onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }

    const form = this.form.getRawValue()
    form.date = this.dateForBackendPipe.transform(this.form.value['date']);
    form.period = format(new Date(this.form.value['period']), 'MMM.yyyy').toString();
    form.newReport = Number(this.form.getRawValue().newReport);
    form.fuelKind = this.form.getRawValue().fuelKind.id;
    form.fuelSupplier = this.form.getRawValue().fuelSupplier.id;
    if (this.currentItemId) {
      form.createdAt = this.dateForBackendPipe.transform(this.form.value['createdAt']);
      if (this.form.get('updatedAt')) {
        form.updatedAt = this.dateForBackendPipe.transform(this.form.value['updatedAt']);
      }
      this.subscriptions.push(this.svc.updateDeclaration(this.currentItemId, form).subscribe({
        next: (res: any) => {
          this.message.success(res.message);
          this.modal.destroy();
        },
        error: (error) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      }));
    } else {
      this.subscriptions.push(this.svc.createDeclaration(form).subscribe({
        next: (res: any) => {
          this.message.success(res.message);
          this.modal.destroy();
        },
        error: (error) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      }));
    }
  }

  private getOptions() {
    this.subscriptions.push(this.fuelService.fetchLatestFuels().subscribe((res) => {
      res.forEach((item: FuelType) => {
        this.fuelKindOptions.push({value: item, label: item.name});
      })
    }));
    this.subscriptions.push(this.fuelService.fetchLatestSuppliers().subscribe((res) => {
      res.forEach((item: FuelProviderType) => {
        this.fuelSupplierOptions.push({value: item, label: item.name});
      })
    }));
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
