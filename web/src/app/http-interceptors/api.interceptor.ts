import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs';

import {AppConfigService} from '../app-config.service';
import {AuthenticationService} from "../auth/auth.service";

@Injectable({providedIn: 'root'})
export class ApiInterceptor implements HttpInterceptor {
  constructor(private appConfigService: AppConfigService,private authService: AuthenticationService,) {}

  public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let url = req.url;
    if (
      !req.url.includes('assets') &&
      !req.url.includes('config')
    ) {
      let apiUrl = this.appConfigService.config?.backend.baseUrl;
      url = `${apiUrl}${req.url}`;
    }

    url = url.replace(/([^:]\/)\/+/g, '$1'); // remove double slash
    let token;
    if(localStorage.getItem('currentUser')){
      // @ts-ignore
      token=JSON.parse(localStorage.getItem('currentUser')).token;
    }

    const apiReq = req.clone({
      url,
      headers: token ? new HttpHeaders({ Authorization: `Bearer ${token}` }):undefined
    });
    return next.handle(apiReq);
  }
}
