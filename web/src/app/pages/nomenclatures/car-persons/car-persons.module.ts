import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CarPersonsListModule } from './car-persons-list/car-persons-list.module';
import { CarPersonsFormModule } from './car-persons-form';
import { CarsBrandsRoutingModule } from './car-persons.routing';
import {CarPersonsService} from "./car-persons.service";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CarPersonsListModule,
    CarPersonsFormModule,
    CarsBrandsRoutingModule
  ],
  exports: [],
  providers: [CarPersonsService]
})
export class CarPersonsModule {}
