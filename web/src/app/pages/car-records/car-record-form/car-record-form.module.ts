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

import { FormsModule as LibFormModule } from '../../../shared/forms';

import { CarRecordFormComponent } from './car-record-form.component';
import {FormsModule as SharedForms} from "../../../shared/forms";
import {DatepickerModule, NumberInputModule, SelectModule, TextInputModule} from "../../../shared/input";
import {DateTimeForBackendPipe, DateTimePipe} from "../../../shared/formatters";
import {FuelsPipe} from "../../../shared/formatters/fuels";
import {VehicleTypePipe} from "../../../shared/formatters/vehicle-type";
import {CategoryTypePipe} from "../../../shared/formatters/category-type";

@NgModule({
  declarations: [CarRecordFormComponent],
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
        SharedForms,
        FormsModule,
        TextInputModule,
        NumberInputModule,
        DatepickerModule,
        SelectModule,
    ],
  exports: [CarRecordFormComponent],
  providers: [NzModalService,DateTimeForBackendPipe,DateTimePipe,FuelsPipe,VehicleTypePipe,CategoryTypePipe]
})
export class CarRecordFormModule {}
