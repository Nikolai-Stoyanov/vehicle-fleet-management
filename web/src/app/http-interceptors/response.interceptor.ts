import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpErrorResponse} from '@angular/common/http';

import {catchError} from 'rxjs/operators';
import {throwError, Observable} from 'rxjs';


@Injectable({providedIn: 'root'})
export class ResponseInterceptor implements HttpInterceptor {

  constructor(  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        switch ((error as HttpErrorResponse).status) {
          case 400:
            return this.handle400Error(error);
          case 401:
            return this.handle401Error(error);
          default:
            return this.handleDefault(error);
        }
      })
    );
  }

  handleDefault(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(error);
  }

  handle401Error(errorObj: any): Observable<any> {
    return throwError(errorObj);
  }

  handle400Error(error: any): Observable<any> {
    // if (error && error.status === 400 && error.error) {
    // 	return this.logoutUser();
    // }

    return throwError(error);
  }
}
