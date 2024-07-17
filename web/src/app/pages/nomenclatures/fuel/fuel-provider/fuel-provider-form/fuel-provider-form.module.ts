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

import { FormsModule as LibFormModule } from '../../../../../shared/forms';
import { FuelProviderFormComponent } from './fuel-provider-form.component';
import {NumberInputModule, TextInputModule, DatepickerModule, SelectModule} from "../../../../../shared/input";

@NgModule({
  declarations: [FuelProviderFormComponent],
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
        NzCardModule,
        TextInputModule,
        LibFormModule,
        DatepickerModule,
        NumberInputModule,
        FormsModule,
        TextInputModule,
        NumberInputModule,
        SelectModule
    ],
  exports: [FuelProviderFormComponent],
  providers: [NzModalService]
})
export class CarsTypeFormModule {}
