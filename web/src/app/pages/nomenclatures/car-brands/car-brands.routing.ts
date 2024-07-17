import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CarBrandsListComponent } from './car-brands-list/car-brands-list.component';

const routes: Routes = [{ path: '', component: CarBrandsListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarsBrandsRoutingModule {
  static components = [CarBrandsListComponent];
}
