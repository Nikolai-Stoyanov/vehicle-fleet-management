import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { NzSelectModule } from 'ng-zorro-antd/select';

import { ClientSelectComponent } from './select.component';
import {ValidationErrorI18NModule} from "../../forms";

@NgModule({
  declarations: [ClientSelectComponent],
    imports: [CommonModule, ReactiveFormsModule, FormsModule, ValidationErrorI18NModule, NzSelectModule, ValidationErrorI18NModule],
  exports: [ClientSelectComponent]
})
export class SelectModule {}
