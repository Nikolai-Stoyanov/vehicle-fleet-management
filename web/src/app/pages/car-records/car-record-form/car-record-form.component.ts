import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import {CarRecordService} from '../car-record.service';
import {
  CarRecord,
  CATEGORYOPTIONS,
  FUELOPTIONS,
  VEHICLEOPTIONS,
} from "../car-record";

import {Subscription} from "rxjs";
import {CarModelsService} from "../../nomenclatures/car-models/car-models.service";
import {DateTimeForBackendPipe, DateTimePipe} from "../../../shared/formatters";
import {CarPersonsService} from "../../nomenclatures/car-persons/car-persons.service";
import {CarPerson} from "../../nomenclatures/car-persons/car-person";
import {FuelsPipe} from "../../../shared/formatters/fuels";
import {VehicleTypePipe} from "../../../shared/formatters/vehicle-type";
import {CategoryTypePipe} from "../../../shared/formatters/category-type";
import {NzMessageService} from "ng-zorro-antd/message";

@Component({
  selector: 'vfm-car-record-form',
  templateUrl: './car-record-form.component.html',
  styleUrls: ['./car-record-form.component.scss'],
})
export class CarRecordFormComponent implements OnInit, OnDestroy {
  public form!: FormGroup;
  @Input() public currentItemId: any;
  public currentItem: any;
  public fuelOptions=FUELOPTIONS
  public categoryOptions = CATEGORYOPTIONS;
  public vehicleOptions = VEHICLEOPTIONS;
  public carModelOptions: any[] = [];
  public carPersonOptions: any[] = [];
  public subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private modal: NzModalRef,
    private message: NzMessageService,
    private svc: CarRecordService,
    private carModelService: CarModelsService,
    private carPersonService: CarPersonsService,
    private dateForBackendPipe: DateTimeForBackendPipe,
    private dateTimePipe: DateTimePipe,
    private fuelPipe:FuelsPipe,
    private vehicleTypePipe:VehicleTypePipe,
    private categoryTypePipe:CategoryTypePipe,
  ) {
    this.currentItemId = this.modal['config'].nzData.id;
  }

  public ngOnInit(): void {
    this.getOptions()
    if (this.currentItemId) {
      this.svc.fetchRecordById(this.currentItemId).subscribe((res: CarRecord) => {
        this.currentItem = res;
        this.getForm()
      });
    } else {
      this.getForm()
    }
  }

  private getForm() {
    this.form = this.fb.group({
      id: this.fb.control({value: this.currentItem?.id || null, disabled: true,}),
      drivingCategory: this.fb.control(
        this.categoryTypePipe.transform(this.currentItem?.drivingCategory) || null, Validators.required),
      description: this.fb.control(this.currentItem?.description || null),
      registrationCertificateData: this.fb.group({
        registrationNumber: this.fb.control(
          {value: this.currentItem?.registrationCertificateData?.registrationNumber || null,
            disabled: this.currentItemId}, Validators.required),
        firstRegistration: this.fb.control(
          this.dateTimePipe.transform(this.currentItem?.registrationCertificateData?.firstRegistration) || null,
          Validators.required),
        vehicleType: this.fb.control(
          this.vehicleTypePipe.transform(this.currentItem?.registrationCertificateData?.vehicleType) || null,
          Validators.required),
        brand: this.fb.control(
          {value: this.currentItem?.registrationCertificateData?.model?.brandName || null, disabled: true},
          Validators.required),
        model: this.fb.control(
          {value:this.currentItem?.registrationCertificateData?.model || null,disabled:this.currentItemId},
          Validators.required),
        seatingCapacity: this.fb.control(
          this.currentItem?.registrationCertificateData?.seatingCapacity-1 || null, Validators.required),
        frameNumber: this.fb.control(
          this.currentItem?.registrationCertificateData?.frameNumber || null, Validators.required),
        engineNumber: this.fb.control(
          this.currentItem?.registrationCertificateData?.engineNumber || null, Validators.required),
        horsePower: this.fb.control(
          this.currentItem?.registrationCertificateData?.horsePower || null, Validators.required),
        enginePower: this.fb.control(
          this.currentItem?.registrationCertificateData?.enginePower || null, Validators.required),
        engineVolume: this.fb.control(
          this.currentItem?.registrationCertificateData?.engineVolume || null, Validators.required),
        primaryColor: this.fb.control(
          this.currentItem?.registrationCertificateData?.primaryColor || null, Validators.required),
        additionalColor: this.fb.control(
          this.currentItem?.registrationCertificateData?.additionalColor || null),
        loadCapacity: this.fb.control(
          this.currentItem?.registrationCertificateData?.loadCapacity || null)
      }),
      owner: this.fb.control(this.currentItem?.owner || null, Validators.required),
      department: this.fb.control(this.currentItem?.department || null, Validators.required),
      stay: this.fb.control(this.currentItem?.stay || null, Validators.required),
      responsible: this.fb.control(
        {value:this.currentItem?.responsible || null,disabled:this.currentItemId}, Validators.required),
      driver: this.fb.control(
        {value:this.currentItem?.driver || null,disabled:this.currentItemId}, Validators.required),
      totalMileage: this.fb.control(this.currentItem?.totalMileage || null, Validators.required),
      developmentFromMileage: this.fb.control(
        this.currentItem?.developmentFromMileage || null, Validators.required),
      developmentToMileage: this.fb.control(this.currentItem?.developmentToMileage || null, Validators.required),
      status: this.fb.control(this.currentItem?.status || null, Validators.required),
      fuelCard: this.fb.control(this.currentItem?.fuelCard || null, Validators.required),
      fuelType: this.fb.control(this.fuelPipe.transform(this.currentItem?.fuelType) || null, Validators.required),
      createdBy: this.fb.control({value: this.currentItem?.createdBy || null, disabled: true}),
      createdAt: this.fb.control({value: this.dateTimePipe.transform(this.currentItem?.createdAt) || null, disabled: true}),
      updatedBy: this.fb.control({value: this.currentItem?.updatedBy || null, disabled: true}),
      updatedAt: this.fb.control({value: this.dateTimePipe.transform(this.currentItem?.updatedAt) || null, disabled: true}),
    });

    this.form.controls['registrationCertificateData'].get('model')?.valueChanges.subscribe(res => {
      this.form.controls['registrationCertificateData'].get('brand')?.setValue(res.brandName)
    })
  }

  public onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }

    const form = this.form.getRawValue()
    form.registrationCertificateData.seatingCapacity=
      this.form.value['registrationCertificateData']['seatingCapacity']+1
    form.registrationCertificateData.firstRegistration =
      this.dateForBackendPipe.transform(this.form.value['registrationCertificateData']['firstRegistration']);
    form.drivingCategory=this.categoryTypePipe.transform( this.form.value.drivingCategory);
    form.fuelType=this.fuelPipe.transform( this.form.value.fuelType);
    form.registrationCertificateData.vehicleType=
      this.vehicleTypePipe.transform( this.form.value['registrationCertificateData']['vehicleType']);

    if (this.currentItemId) {
      form.createdAt = this.dateForBackendPipe.transform(this.form.value['createdAt']);
      if (this.form.get('updatedAt')) {
        form.updatedAt = this.dateForBackendPipe.transform(this.form.value['updatedAt']);
      }
      const sub1 = this.svc.updateRecords(this.currentItemId, form).subscribe({
        next: (res:any) => {
          this.message.success(res.message);
          this.modal.destroy();
        },
        error: (error) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      });
      this.subscriptions.push(sub1);
    } else {
      const sub2 = this.svc.createRecords(form).subscribe({
        next: (res:any) => {
          this.message.success(res.message);
          this.modal.destroy();
        },
        error: (error) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      });
      this.subscriptions.push(sub2);
    }
  }

  private getOptions() {
    this.carModelService.fetchLatest().subscribe((res) => {
      res.forEach((item: any) => {
        this.carModelOptions.push({value: item, label: item.name});
      })
    })
    this.carPersonService.fetchLatest().subscribe((res) => {
      res.forEach((item: CarPerson) => {
        this.carPersonOptions.push({value: item, label: item.fullName});
      })
    })
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
