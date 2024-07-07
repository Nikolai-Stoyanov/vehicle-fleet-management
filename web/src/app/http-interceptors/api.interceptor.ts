import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs';

import {AppConfigService} from '../app-config.service';

@Injectable({providedIn: 'root'})
export class ApiInterceptor implements HttpInterceptor {
  constructor(private appConfigService: AppConfigService) {}

  public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let url = req.url;
    const apiUrl = this.appConfigService.config?.backend.baseUrl;
    url = `${apiUrl}${req.url}`;

    url = url.replace(/([^:]\/)\/+/g, '$1'); // remove double slash

    const apiReq = req.clone({
      url,
      headers: new HttpHeaders()
    });
    return next.handle(apiReq);
  }
}
