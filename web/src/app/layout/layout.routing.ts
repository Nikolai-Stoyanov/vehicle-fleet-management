import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutComponent } from './layout.component';
import { DefaultLayoutComponent } from './default-layout';
import { MenuComponent } from './menu/menu.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'waybill',
      },
      {
        path: 'home',
        loadChildren: () => import('../home/home.module').then((m) => m.HomeModule)
      },
      {
        path: '**',
        redirectTo: '',
      },
    ]
  }
];

export const LayoutRouting: ModuleWithProviders<RouterModule> = RouterModule.forChild(routes);

export class LayoutComponents {
  public static components = [DefaultLayoutComponent, LayoutComponent, MenuComponent];
}
