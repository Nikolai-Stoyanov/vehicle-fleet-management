import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {CarBrandsService} from "../../car-brands/car-brands.service";
import {DateTimePipe} from "../../../../shared/formatters/date-time";

@Component({
  selector: 'vfm-car-models-form',
  templateUrl: './car-models-form.component.html',
  styleUrls: ['./car-models-form.component.scss'],
})
export class CarModelsFormComponent implements OnInit {
  public form!: FormGroup;
  @Input() public currentItem: any;
  @Output() public submit = new EventEmitter<any>();
  brandOptions: any[]=[];

  constructor(
    private fb: FormBuilder,
    private modalService: NzModalService,
    private message: NzMessageService,
    protected modal: NzModalRef,
    private brandService:CarBrandsService,
    private dateTimePipe:DateTimePipe
  ) {
    this.currentItem = this.modal['config'].nzData.currentItem;

  }

  ngOnInit(): void {
    this.brandService.carBrandsListData.forEach(item=>{
      this.brandOptions.push({label: item.name, value: item.id})
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
      brand: this.fb.control(
        this.currentItem?.brand || null,
        Validators.required
      ),
      year: this.fb.control(
        this.dateTimePipe.transform(this.currentItem?.year)|| null,
        Validators.required
      ),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }

  onSubmit() {
    console.log(this.form);
    // this.submit.emit(this.form.getRawValue());
  }

  onChange($event: any) {

  }
}
