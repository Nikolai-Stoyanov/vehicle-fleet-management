import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzResizableModule } from 'ng-zorro-antd/resizable';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';


import { DummyTableCheckboxesComponent } from './dummy-table-checkboxes.component';
import {ArrStringModule} from "../formatters/arr-string/arr-string.module";
import {DateTimeModule} from "../formatters";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NzTableModule,
    NzIconModule,
    NzResizableModule,
    NzButtonModule,
    NzPaginationModule,
    NzDropDownModule,
    NzInputModule,
    NzToolTipModule,
    NzGridModule,
    ArrStringModule,
    DateTimeModule
  ],
  exports: [DummyTableCheckboxesComponent],
  declarations: [DummyTableCheckboxesComponent]
})
export class DummyTableCheckboxesModule {}
