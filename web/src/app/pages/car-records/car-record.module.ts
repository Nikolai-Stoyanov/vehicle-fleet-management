import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CarRecordRouting } from './car-record.routing';
import { CarRecordListModule } from './car-record-list/car-record-list.module';
import { CarRecordFormModule } from './car-record-form';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CarRecordRouting,
    CarRecordListModule,
    CarRecordFormModule
  ],
  exports: [],
  providers: []
})
export class CarRecordModule {}
