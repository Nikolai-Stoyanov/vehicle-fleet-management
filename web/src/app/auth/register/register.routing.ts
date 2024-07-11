import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register.component';

const routes: Routes = [{ path: '', component: RegisterComponent }];

export const RegisterRouting: ModuleWithProviders<RouterModule> = RouterModule.forChild(routes);

export class LoginComponents {
  public static components = [RegisterComponent];
}
