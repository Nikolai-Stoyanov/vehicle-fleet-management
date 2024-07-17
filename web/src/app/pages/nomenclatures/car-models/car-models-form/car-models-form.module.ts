import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzModalService } from 'ng-zorro-antd/modal';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzDescriptionsModule } from 'ng-zorro-antd/descriptions';
import { NzSpaceModule } from 'ng-zorro-antd/space';


import { CarModelsFormComponent } from './car-models-form.component';
import {FormsModule as SharedFormsModule} from "../../../../shared/forms";
import {TextInputModule} from "../../../../shared/input/text-input";
import {DatepickerModule} from "../../../../shared/input/datepicker";
import {SelectModule} from "../../../../shared/input/select";
import {NzDatePickerComponent} from "ng-zorro-antd/date-picker";
import {DateTimeModule} from "../../../../shared/formatters/date-time/date-time.module";
import {DateTimePipe} from "../../../../shared/formatters/date-time";

@NgModule({
  declarations: [CarModelsFormComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NzGridModule,
    NzButtonModule,
    NzIconModule,
    NzCheckboxModule,
    NzInputModule,
    NzToolTipModule,
    NzDividerModule,
    NzCardModule,
    NzDescriptionsModule,
    NzSpaceModule,
    SharedFormsModule,
    TextInputModule,
    DatepickerModule,
    SelectModule,
    NzDatePickerComponent,
    DateTimeModule
  ],
  exports: [CarModelsFormComponent],
  providers: [NzModalService,DateTimePipe]
})
export class CarModelsFormModule {}
