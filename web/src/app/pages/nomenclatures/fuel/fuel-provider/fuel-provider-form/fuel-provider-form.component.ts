import {Component, OnDestroy, OnInit} from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import {FuelService} from "../../fuel.service";
import {FuelProviderType, FuelType} from "../../fuel";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-fuel-provider-form',
  templateUrl: './fuel-provider-form.component.html',
  styleUrls: ['./fuel-provider-form.component.scss']
})
export class FuelProviderFormComponent implements OnInit, OnDestroy  {
  public form!: FormGroup;
  public currentItem: any;
  public currentItemId
  public fuelOptions: any[]=[];
  public subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    private modal: NzModalRef,
    private svc: FuelService,
  ) {
    this.currentItemId = this.modal['config'].nzData.id;
  }

  ngOnInit(): void {
    this.subscriptions.push(this.svc.fetchLatestFuels().subscribe((res:FuelType[]) => {
      res.forEach(item => {
        this.fuelOptions.push({ label: item.name,value: item});
      })
    }));
    if (this.currentItemId) {
      this.subscriptions.push(this.svc.fetchSupplierById(this.currentItemId).subscribe((res:FuelProviderType) => {
        this.currentItem = res;
        this.getForm();
      }));
    }else {
      this.getForm();
    }
  }

  getForm(){
    this.form = this.fb.group({
      id: this.fb.control({value: this.currentItem?.id || null, disabled: true}),
      name: this.fb.control(this.currentItem?.name || null, Validators.required),
      description: this.fb.control(this.currentItem?.description || null),
      fuelOptions: this.fb.array(
        this.currentItem?.fuelList
          ? this.currentItem.fuelList.map((options: any) =>
            this.fb.group({
              name: this.fb.control(options  || null,
                Validators.required)
            })
          )
          : []
      ),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }


  get getFuelOptionsControls(): any {
    return (this.form.get('fuelOptions') as FormArray).controls;
  }

  public addFuelOption() {
    (this.form.get('fuelOptions') as FormArray).push(
      this.fb.group({
        name: this.fb.control(null, Validators.required)
      })
    );
  }

  public removeFuelOption(index: any) {
    (this.form.get('fuelOptions') as FormArray).removeAt(index);
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
      fuelList:this.form.getRawValue().fuelOptions.map((options: any) =>options.name.id),
    }

    if (!this.currentItem?.id) {
      this.subscriptions.push(this.svc.createSupplier(formObject).subscribe({
        next: (res) => {
          this.message.success(res.message);
          this.modal.destroy();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      }));
    }else {
      this.subscriptions.push(this.svc.updateSupplier(this.currentItem?.id,formObject).subscribe({
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
