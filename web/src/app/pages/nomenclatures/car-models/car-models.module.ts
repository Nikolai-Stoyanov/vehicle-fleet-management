import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CarModelsListModule } from './car-models-list/car-models-list.module';
import { CarModelsFormModule } from './car-models-form';
import { CarsBrandsRoutingModule } from './car-models.routing';
import {CarModelsService} from "./car-models.service";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CarModelsListModule,
    CarModelsFormModule,
    CarsBrandsRoutingModule
  ],
  exports: [],
  providers: [CarModelsService]
})
export class CarModelsModule {}
