import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CarRecordFormComponent } from './car-record-form';
import { CarRecordListComponent } from './car-record-list/car-record-list.component';

const routes: Routes = [
  {
    path: '',
    component: CarRecordListComponent
  },
  {
    path: 'new',
    data: { breadcrumb: 'newMeasuringPoints' },
    component: CarRecordFormComponent
  },
  {
    path: ':id',
    children: [
      {
        path: 'view',
        data: { breadcrumb: 'viewMeasuringPoints' },
        component: CarRecordFormComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarRecordRouting {}
