import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import {provideHttpClient} from "@angular/common/http";
import {provideAnimations} from "@angular/platform-browser/animations";

import { routes } from './app.routes';
import {appBootstrap} from "./app-bootstrap";
import {AppConfigService} from "./app-config.service";
import {httpInterceptorProviders} from "./http-interceptors";
import {environment} from "../environments/environment";
import {ENVIRONMENT} from "./shared/shared";


export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideAnimations(),
   AppConfigService, appBootstrap, httpInterceptorProviders,
    { provide: ENVIRONMENT, useValue: environment }
  ]
};
