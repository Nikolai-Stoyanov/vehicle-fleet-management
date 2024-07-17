import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { NzInputNumberModule } from 'ng-zorro-antd/input-number';

import { NumberInputComponent } from './number-input.component';
import {ValidationErrorI18NModule} from "../../forms";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NzInputNumberModule,
        ValidationErrorI18NModule,
        ValidationErrorI18NModule
    ],
  exports: [NumberInputComponent],
  declarations: [NumberInputComponent]
})
export class NumberInputModule {}
