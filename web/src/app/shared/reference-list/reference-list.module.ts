import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzModalModule } from 'ng-zorro-antd/modal';

import { FormsModule as LIBSFormsModule } from '../forms';
import {DateTimeModule, DateTimePipe} from '../formatters/date-time';

import { ReferenceListComponent } from './reference-list.component';
import {DummyTableCheckboxesModule} from "../dummy-table";
import {TextAreaModule} from "../input/textarea";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NzGridModule,
    NzButtonModule,
    NzTableModule,
    NzIconModule,
    NzModalModule,
    LIBSFormsModule,
    DateTimeModule,
    DummyTableCheckboxesModule,
    TextAreaModule,
  ],
  declarations: [ReferenceListComponent],
  exports: [ReferenceListComponent],
  providers:[DateTimePipe]
})
export class ReferenceListModule {}
