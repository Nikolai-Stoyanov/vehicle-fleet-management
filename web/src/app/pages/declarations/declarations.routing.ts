import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DeclarationsListComponent } from './declarations-list/declarations-list.component';

const routes: Routes = [{ path: '', component: DeclarationsListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DeclarationsRoutingModule {
  static components = [DeclarationsListComponent];
}
