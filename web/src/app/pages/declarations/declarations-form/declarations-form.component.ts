import { Component,  OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import {Fuels} from "../../car-records/car-record";
import {CarRecordService} from "../../car-records/car-record.service";
import {DeclarationsService} from "../declarations.service";

@Component({
  selector: 'ap-declarations-form',
  templateUrl: './declarations-form.component.html',
  styleUrls: ['./declarations-form.component.scss'],
})
export class DeclarationsFormComponent implements OnInit {
  public form!: FormGroup;
  public currentItem: any;
  public currentItemId: any;
  public registrationNumber:any

  constructor(
    private fb: FormBuilder,
    private modal: NzModalRef,
    private svc: DeclarationsService,
    private carRecordService: CarRecordService,
  ) {
    this.currentItemId = this.modal['config'].nzData.currentId;
    this.registrationNumber = this.modal['config'].nzData.registrationNumber;
  }

  ngOnInit(): void {
    if (this.currentItem) {
      this.svc.fetchDeclarationById(this.currentItemId).subscribe((res: any) => {
        this.currentItem = res;
        this.getForm()
      });
    } else {
      this.carRecordService.fetchRecordById(this.registrationNumber?.carId).subscribe((res: any) => {});
      this.currentItem = {
        oldReport:120000,
        responsible:'Pesho',
        registrationNumber:this.registrationNumber?.registrationNumber,
        fuels:[
          {
            type: Fuels.GASOLINE,
            tankCapacity: 50,
            distanceLimit: 300,
            moneyLimit: 200,
          },
          {
            type: Fuels.LPG,
            tankCapacity: 50,
            distanceLimit: 300,
            moneyLimit: 200,
          }
        ]
      };
      this.getForm()
    }
  }

  private getForm() {
    this.form = this.fb.group({
      id: this.fb.control({
        value: this.currentItem?.id || null,
        disabled: true,
      }),
      dateFrom: this.fb.control(
        this.currentItem?.dateFrom || null,
        Validators.required
      ),
      period: this.fb.control(
        this.currentItem?.period || null,
        Validators.required
      ),
      oldReport: this.fb.control(
        {value:this.currentItem?.oldReport || null,disabled:true},
        Validators.required
      ),
      responsible: this.fb.control(
        {value:this.currentItem?.responsible || null,disabled:true},
        Validators.required
      ),
      newReport: this.fb.control(
        this.currentItem?.newReport || null,
        Validators.required
      ),
      registrationNumber: this.fb.control(
        {value:this.currentItem?.registrationNumber || null,disabled:true},
        Validators.required
      ),
      mileage: this.fb.control(
        this.currentItem?.mileage || null,
        Validators.required
      ),
      fuels: this.fb.array(
        this.currentItem?.fuels
          ? this.currentItem.fuels.map((options: any) =>
            this.fb.group({
              type: this.fb.control(options.type || null,
                Validators.required),
              tankCapacity: this.fb.control(options.tankCapacity || null,
                Validators.required),
              fuelRemaining: this.fb.control(options.fuelRemaining || null),
              fuelConsumption: this.fb.control(options.fuelConsumption || null),
              distanceLimit: this.fb.control(options.distanceLimit || null),
              distanceRemaining: this.fb.control(options.distanceRemaining || null),
              moneyLimit: this.fb.control(options.moneyLimit || null),
              mileage: this.fb.control(options.moneyLimit || null)
            })
          )
          : []
      ),
      createdBy: this.fb.control(
        {value: this.currentItem?.createdBy || null,disabled:true},
        Validators.required
      ),
      createdDate: this.fb.control(
        {value:this.currentItem?.createdDate || null,disabled:true},
        Validators.required
      ),
      modifyBy: this.fb.control(
        {value:this.currentItem?.modifyBy || null,disabled:true},
        Validators.required
      ),
      modifyDate: this.fb.control(
        {value:this.currentItem?.modifyDate || null,disabled:true},
        Validators.required
      ),
    });
console.log(this.form.getRawValue(),this.currentItem)
  }

  get getFuelOptionsControls(): any {
    return (this.form.get('fuels') as FormArray).controls;
  }

  onSubmit() {
    console.log(this.form.value);
    // this.submit.emit(this.form.getRawValue());
  }
}
