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


import { EditFormComponent } from './edit-form.component';
import {FormsModule as SharedFormsModule} from "../../forms";
import {NzDatePickerComponent} from "ng-zorro-antd/date-picker";
import {DateTimeModule} from "../../formatters";
import {DateTimePipe} from "../../formatters";
import {DatepickerModule, NumberInputModule, SelectModule, TextInputModule} from "../../input";

@NgModule({
  declarations: [EditFormComponent],
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
        DateTimeModule,
        FormsModule,
        DatepickerModule,
        SelectModule,
        TextInputModule,
        NumberInputModule
    ],
  exports: [EditFormComponent],
  providers: [NzModalService,DateTimePipe]
})
export class EditFormModule {}
