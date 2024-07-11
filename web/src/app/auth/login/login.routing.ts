import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login.component';

const routes: Routes = [{ path: '', component: LoginComponent }];

export const LoginRouting: ModuleWithProviders<RouterModule> = RouterModule.forChild(routes);

export class LoginComponents {
  public static components = [LoginComponent];
}
