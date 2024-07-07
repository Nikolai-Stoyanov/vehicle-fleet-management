import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { tap } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { RuntimeConfiguration } from './shared/shared';

const CONFIG_URL: string = environment.appConfig.url;

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {
  private appConfig: RuntimeConfiguration | undefined;
  private url: string = CONFIG_URL;
  private loaded = false;


  constructor(private http: HttpClient) {}

  public loadAppConfig() {
    return this.http
      .get<RuntimeConfiguration>(this.url)
      .pipe(
        tap((config: RuntimeConfiguration) => {
          this.appConfig = config;
          this.loaded = true;
        })
      )
      .toPromise();
  }

  public get config() {
    return this.appConfig;
  }

  public get isLoaded() {
    return this.loaded;
  }
}
