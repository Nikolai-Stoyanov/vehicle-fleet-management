import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutComponent } from './layout.component';
import { DefaultLayoutComponent } from './default-layout';
import { MenuComponent } from './menu/menu.component';
import { PermissionGuard } from '../core/guards/permission.guard';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'home',
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
        path: 'car-brands',
        loadChildren: () => import('../pages/nomenclatures/car-brands').then((m) => m.CarBrandsModule),
        canActivate: [PermissionGuard],
        data: {
          expectedPermission: ['USER','ADMIN'],
          module: $localize`Car brands`
        },
      },
      {
        path: 'car-models',
        loadChildren: () => import('../pages/nomenclatures/car-models').then((m) => m.CarModelsModule),
        canActivate: [PermissionGuard],
        data: {
          expectedPermission: ['USER','ADMIN'],
          module: $localize`Car models`
        },
      },
      {
        path: 'fuel',
        loadChildren: () => import('../pages/nomenclatures/fuel').then((m) => m.FuelModule),
        canActivate: [PermissionGuard],
        data: {
          expectedPermission: ['USER','ADMIN'],
          module: $localize`Fuel`
        },
      },
      {
        path: 'car-persons',
        loadChildren: () => import('../pages/nomenclatures/car-persons').then((m) => m.CarPersonsModule),
        canActivate: [PermissionGuard],
        data: {
          expectedPermission: ['USER','ADMIN'],
          module: $localize`Car persons`
        },
      },
      {
        path: 'car-record',
        loadChildren: () => import('../pages/car-records').then((m) => m.CarRecordModule),
        canActivate: [PermissionGuard],
        data: {
          expectedPermission: ['USER','ADMIN'],
          module: $localize`Car records`
        },
      },
      {
        path: 'declarations',
        loadChildren: () => import('../pages/declarations').then((m) => m.DeclarationsModule),
        canActivate: [PermissionGuard],
        data: {
          expectedPermission: ['USER','ADMIN'],
          module: $localize`Declarations`
        },
      },
      {
        path: 'users',
        loadChildren: () => import('../pages/nomenclatures/users').then((m) => m.UsersModule),
        canActivate: [PermissionGuard],
        data: {
          expectedPermission: ['ADMIN'],
          module: $localize`Users`
        },
      },
      {
        path: '**',
        redirectTo: 'home',
      },
    ]
  }
];

export const LayoutRouting: ModuleWithProviders<RouterModule> = RouterModule.forChild(routes);

export class LayoutComponents {
  public static components = [DefaultLayoutComponent, LayoutComponent, MenuComponent];
}
