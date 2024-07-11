import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CarsBrandsListModule } from './cars-brands-list/cars-brands-list.module';
import { CarsBrandsFormModule } from './cars-brands-form';
import { CarsBrandsRoutingModule } from './cars-brands.routing';
import {CarBrandsService} from "./car-brands.service";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CarsBrandsListModule,
    CarsBrandsFormModule,
    CarsBrandsRoutingModule
  ],
  exports: [],
  providers: [CarBrandsService]
})
export class CarsBrandsModule {}
