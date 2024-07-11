import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { BehaviorSubject, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ENVIRONMENT } from '../shared/shared';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser$: Observable<any>;

  constructor(
    private httpClient: HttpClient,
    @Inject(ENVIRONMENT) protected env: any // private appConfigService: AppConfigService
  ) {
    this.currentUserSubject = new BehaviorSubject<any>(null);
    this.currentUser$ = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login(form: any): any {
    const body = this.getBody(form);
    return this.httpClient.post<any>('/login', body).pipe(
      mergeMap((res) => {
        return this.redirectTo(res);
      })
    );
  }

  getBody(form: any): any {
    const { username, password, language, appId, redirectTo } = form;
    return {
      user: username,
      password,
      language: language,
      applicationId: appId,
      redirectTo: redirectTo
    };
  }

  redirectTo(res: any): any {
    console.log('Redirecting to: ' + res.redirectUrl);
    if (res.openInNewTab) {
      return window.open(res.redirectUrl, '_blank');
    }
    return (window.location.href = res.redirectUrl);
  }
}
