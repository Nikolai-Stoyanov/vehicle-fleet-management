import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';

export const routes: Routes = [
  {
    path:'',
    loadChildren: () => import('./layout/layout.module').then((m) => m.LayoutModule)
  }
];
