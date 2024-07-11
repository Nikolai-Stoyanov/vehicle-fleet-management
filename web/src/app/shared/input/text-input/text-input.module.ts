import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { NzInputModule } from 'ng-zorro-antd/input';
import { NzIconModule } from 'ng-zorro-antd/icon';

import { TextInputComponent } from './text-input.component';
import { ValidationErrorI18NModule } from '../../forms';

@NgModule({
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NzInputModule, NzIconModule, ValidationErrorI18NModule],
  exports: [TextInputComponent],
  declarations: [TextInputComponent]
})
export class TextInputModule {}
