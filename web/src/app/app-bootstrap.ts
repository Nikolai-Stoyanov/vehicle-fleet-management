import { APP_INITIALIZER } from '@angular/core';

import { AppConfigService } from './app-config.service';

export function bootstrap(appConfigService: AppConfigService) {
  return async () => {
    console.log('Bootstrapping');
    await appConfigService.loadAppConfig();
    console.log('Bootstrapped');
  };
}

export const appBootstrap = {
  provide: APP_INITIALIZER,
  multi: true,
  useFactory: bootstrap,
  deps: [AppConfigService]
};
