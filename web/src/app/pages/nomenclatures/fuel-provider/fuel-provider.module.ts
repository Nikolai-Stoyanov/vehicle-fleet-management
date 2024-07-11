import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FuelProviderListModule } from './fuel-provider-list/fuel-provider-list.module';
import { CarsTypeFormModule } from './fuel-provider-form';
import { FuelProviderRoutingModule } from './fuel-provider.routing';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FuelProviderListModule,
    CarsTypeFormModule,
    FuelProviderRoutingModule
  ],
  exports: [],
  providers: []
})
export class FuelProviderModule {}
