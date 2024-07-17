import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CarBrandsListModule } from './car-brands-list/car-brands-list.module';
import { CarBrandsFormModule } from './car-brands-form';
import { CarsBrandsRoutingModule } from './car-brands.routing';
import {CarBrandsService} from "./car-brands.service";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CarBrandsListModule,
    CarBrandsFormModule,
    CarsBrandsRoutingModule
  ],
  exports: [],
  providers: [CarBrandsService]
})
export class CarBrandsModule {}
