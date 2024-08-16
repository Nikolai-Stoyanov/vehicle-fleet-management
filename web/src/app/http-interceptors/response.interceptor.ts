import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpErrorResponse} from '@angular/common/http';

import {catchError} from 'rxjs/operators';
import {throwError, Observable} from 'rxjs';
import {AuthenticationService} from "../auth/auth.service";
import {NzMessageService} from "ng-zorro-antd/message";


@Injectable({providedIn: 'root'})
export class ResponseInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthenticationService,
    private message: NzMessageService,
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        switch ((error as HttpErrorResponse).status) {
          case 400:
            return this.handle400Error(error);
          case 401:
            return this.handle401Error(req, next, error);
          default:
            return this.handleDefault(error);
        }
      })
    );
  }

  handleDefault(error: HttpErrorResponse) {
    return throwError(error);
  }

  handle401Error(req: HttpRequest<any>, next: HttpHandler, errorObj: any): Observable<any> {
    this.authService.isTokenValid();
    this.message.error(errorObj.status + " " + errorObj.error.message);
    return throwError(errorObj);
  }

  handle400Error(error: any): Observable<any> {
    return throwError(error);
  }
}
