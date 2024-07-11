import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NzModalService } from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'ap-cars-brands-form',
  templateUrl: './cars-brands-form.component.html',
  styleUrls: ['./cars-brands-form.component.scss']
})
export class CarsBrandsFormComponent implements OnInit {
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
      mark: this.fb.control(
        this.currentItem?.mark || null,
        Validators.required
      ),
      model: this.fb.control(
        this.currentItem?.model || null,
        Validators.required
      ),
      manufactureYear: this.fb.control(
        this.currentItem?.manufactureYear || null,
        Validators.required
      ),
      manufacturer: this.fb.control(
        this.currentItem?.manufacturer || null,
        Validators.required
      ),
      active: this.fb.control(this.currentItem?.active || null)
    });
  }

  onSubmit() {
    console.log(this.form);
    // this.submit.emit(this.form.getRawValue());
  }
}
