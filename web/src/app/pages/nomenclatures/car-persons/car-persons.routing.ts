import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CarPersonsListComponent } from './car-persons-list/car-persons-list.component';

const routes: Routes = [{ path: '', component: CarPersonsListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarsBrandsRoutingModule {
  static components = [CarPersonsListComponent];
}
