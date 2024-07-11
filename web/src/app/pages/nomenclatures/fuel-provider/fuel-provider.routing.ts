import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { FuelProviderListComponent } from './fuel-provider-list/fuel-provider-list.component';

const routes: Routes = [{ path: '', component: FuelProviderListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FuelProviderRoutingModule {
  static components = [FuelProviderListComponent];
}
