import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {  FormBuilder, FormGroup, Validators } from '@angular/forms';

import {NzModalRef} from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'vfm-fuel-form',
  templateUrl: './fuel-form.component.html',
  styleUrls: ['./fuel-form.component.scss']
})
export class FuelFormComponent implements OnInit {
  public form!: FormGroup;
  @Input() public currentItem: any;
  @Output() public submit = new EventEmitter<any>();

  constructor(
    private fb: FormBuilder,
    private modal: NzModalRef,
    private message: NzMessageService
  ) {
    this.currentItem = this.modal['config'].nzData.currentItem;
  }

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
      description: this.fb.control(
        this.currentItem?.description || null
      ),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }

  onSubmit() {
    console.log(this.form);
    // this.submit.emit(this.form.getRawValue());
  }
}
