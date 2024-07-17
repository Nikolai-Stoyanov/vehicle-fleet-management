import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'vfm-car-models-form',
  templateUrl: './car-brands-form.component.html',
  styleUrls: ['./car-brands-form.component.scss']
})
export class CarBrandsFormComponent implements OnInit {
  public form!: FormGroup;
  @Input() public currentItem: any;
  @Output() public submit = new EventEmitter<any>();

  constructor(
    private fb: FormBuilder,
    private modalService: NzModalService,
    private message: NzMessageService,
    protected modal: NzModalRef,
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
        this.currentItem?.description || null,
        Validators.required
      ),
      company: this.fb.control(
        this.currentItem?.company || null,
        Validators.required
      ),
      models: this.fb.control(
        {
          value: this.currentItem?.models || null,
          disabled: true
        }
      ),
      status: this.fb.control(this.currentItem?.status || null)
    });
  }

  onSubmit() {
    console.log(this.form);
    // this.submit.emit(this.form.getRawValue());
  }
}
