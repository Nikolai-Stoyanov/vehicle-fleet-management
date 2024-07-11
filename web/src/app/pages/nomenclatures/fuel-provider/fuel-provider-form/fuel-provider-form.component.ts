import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NzModalService } from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'ap-cars-type-form',
  templateUrl: './fuel-provider-form.component.html',
  styleUrls: ['./fuel-provider-form.component.scss']
})
export class FuelProviderFormComponent implements OnInit {
  public form!: FormGroup;
  @Input() public currentItem: any;
  @Output() public submit = new EventEmitter<any>();

  constructor(
    private fb: FormBuilder,
    private modalService: NzModalService,
    private message: NzMessageService
  ) {}

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
      fuelOptions: this.fb.array(
        this.currentItem?.fuelOptions
          ? this.currentItem.fuelOptions.map((options: any) =>
              this.fb.group({
                id: this.fb.control(options.id || null),
                name: this.fb.control(options.name || null),
                price: this.fb.control(options.price || null)
              })
            )
          : []
      ),
      active: this.fb.control(this.currentItem?.active || null)
    });
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

  public removeLoadProfiles(index: any) {
    (this.form.get('fuelOptions') as FormArray).removeAt(index);
  }

  onSubmit() {
    console.log(this.form);
    // this.submit.emit(this.form.getRawValue());
  }
}
