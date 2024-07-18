import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { NzInputModule } from 'ng-zorro-antd/input';

import { TextAreaComponent } from './text-area.component';
import { ValidationErrorI18NModule } from '../../forms';

@NgModule({
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NzInputModule, ValidationErrorI18NModule],
  exports: [TextAreaComponent],
  declarations: [TextAreaComponent]
})
export class TextAreaModule {}
