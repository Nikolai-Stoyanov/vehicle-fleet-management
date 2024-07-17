import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import {FuelService} from "../../fuel.service";
import {FuelType} from "../../fuel";

@Component({
  selector: 'vfm-fuel-provider-form',
  templateUrl: './fuel-provider-form.component.html',
  styleUrls: ['./fuel-provider-form.component.scss']
})
export class FuelProviderFormComponent implements OnInit {
  public form!: FormGroup;
  @Input() public currentItem: any;
  @Output() public submit = new EventEmitter<any>();
  public fuelOptions: any[]=[];

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    private modal: NzModalRef,
    private svc: FuelService,
  ) {
    this.currentItem = this.modal['config'].nzData.currentItem;
  }

  ngOnInit(): void {
    this.svc.fetchLatestFuel().subscribe((res:FuelType[]) => {
      res.forEach(item => {
        this.fuelOptions.push({value: item.id, label: item.name});
      })

    })
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
      fuelOptions: this.fb.array(
        this.currentItem?.fuelOptions
          ? this.currentItem.fuelOptions.map((options: any) =>
              this.fb.group({
                id: this.fb.control(options.id || null,
                  Validators.required),
                name: this.fb.control(options.id  || null,
                  Validators.required),
                price: this.fb.control(options.price || null)
              })
            )
          : []
      ),
      status: this.fb.control(this.currentItem?.status || null)
    });

    this.form.get('fuelOptions')?.valueChanges.subscribe((res)=>{
      console.log(res);
    })
  }

  get getFuelOptionsControls(): any {
    return (this.form.get('fuelOptions') as FormArray).controls;
  }

  public addFuelOption() {
    (this.form.get('fuelOptions') as FormArray).push(
      this.fb.group({
        id: this.fb.control(null),
        name: this.fb.control(null, Validators.required),
        price: this.fb.control(null)
      })
    );
  }

  public removeFuelOption(index: any) {
    (this.form.get('fuelOptions') as FormArray).removeAt(index);
  }

  onSubmit() {
    console.log(this.form);
    // this.submit.emit(this.form.getRawValue());
  }

  onselectionchange(event:any){
    console.log(event);
  };
}
