import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzPageHeaderModule } from 'ng-zorro-antd/page-header';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzAutocompleteModule } from 'ng-zorro-antd/auto-complete';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzCollapseModule } from 'ng-zorro-antd/collapse';
import { NzMessageService } from 'ng-zorro-antd/message';

import { CarRecordListComponent } from './car-record-list.component';
import {DummyTableCheckboxesModule} from "../../../shared/dummy-table";

@NgModule({
  declarations: [CarRecordListComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    NzIconModule,
    NzPageHeaderModule,
    NzButtonModule,
    NzAutocompleteModule,
    NzInputModule,
    NzPaginationModule,
    NzGridModule,
    NzToolTipModule,
    DummyTableCheckboxesModule,
    NzCollapseModule,
    DummyTableCheckboxesModule
  ],
  exports: [CarRecordListComponent],
  providers: [NzMessageService]
})
export class CarRecordListModule {}
