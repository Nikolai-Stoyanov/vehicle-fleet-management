import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import {provideHttpClient, withInterceptors, withInterceptorsFromDi} from "@angular/common/http";
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

    provideAnimations(),
    AppConfigService,
    appBootstrap,
    { provide: ENVIRONMENT, useValue: environment },
    httpInterceptorProviders,
    provideHttpClient(withInterceptorsFromDi()),
  ]
};
