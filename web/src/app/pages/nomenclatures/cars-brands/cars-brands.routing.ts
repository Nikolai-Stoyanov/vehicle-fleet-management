import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CarsBrandsListComponent } from './cars-brands-list/cars-brands-list.component';

const routes: Routes = [{ path: '', component: CarsBrandsListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarsBrandsRoutingModule {
  static components = [CarsBrandsListComponent];
}
