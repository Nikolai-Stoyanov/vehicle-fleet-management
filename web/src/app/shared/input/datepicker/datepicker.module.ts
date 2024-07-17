import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';

import { DatepickerComponent } from './datepicker.component';
import {ValidationErrorI18NModule} from "../../forms";

@NgModule({
  declarations: [DatepickerComponent],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        ValidationErrorI18NModule,
        NzDatePickerModule
    ],
  exports: [DatepickerComponent]
})
export class DatepickerModule {}
