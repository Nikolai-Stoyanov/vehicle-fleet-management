import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CarModelsListComponent } from './car-models-list/car-models-list.component';

const routes: Routes = [{ path: '', component: CarModelsListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarsBrandsRoutingModule {
  static components = [CarModelsListComponent];
}
