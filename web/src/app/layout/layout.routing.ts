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
        path: 'login',
        loadChildren: () => import('../auth/login/login.module').then((m) => m.LoginModule)
      },
      {
        path: 'register',
        loadChildren: () => import('../auth/register/register.module').then((m) => m.RegisterModule)
      },
      {
        path: 'home',
        loadChildren: () => import('../pages/home/home.module').then((m) => m.HomeModule)
      },
      {
        path: 'cars-brands',
        loadChildren: () => import('../pages/nomenclatures/cars-brands').then((m) => m.CarsBrandsModule)
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
