import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import {NzModalComponent, NzModalContentDirective, NzModalService} from 'ng-zorro-antd/modal';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzDescriptionsModule } from 'ng-zorro-antd/descriptions';
import { NzSpaceModule } from 'ng-zorro-antd/space';

import {
  DatepickerModule,
  TextInputModule,
  NumberInputModule,
  TextAreaModule, SelectModule
} from '../../../shared/input';
import { FormsModule as LibFormModule } from '../../../shared/forms';


import { DeclarationsFormComponent } from './declarations-form.component';
import {DateTimeForBackendPipe, DateTimePipe} from "../../../shared/formatters";
import {FuelService} from "../../nomenclatures/fuel/fuel.service";

@NgModule({
  declarations: [DeclarationsFormComponent],
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
    TextInputModule,
    LibFormModule,
    DatepickerModule,
    NumberInputModule,
    TextAreaModule,
    SelectModule,
    NzModalComponent,
    NzModalContentDirective
  ],
  exports: [DeclarationsFormComponent],
  providers: [NzModalService,DateTimeForBackendPipe,FuelService,DateTimePipe]
})
export class DeclarationsFormModule {}
