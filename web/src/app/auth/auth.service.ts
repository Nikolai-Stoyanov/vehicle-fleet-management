import {Injectable, Inject} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {BehaviorSubject, Observable} from 'rxjs';
import {mergeMap, tap} from 'rxjs/operators';

import {ENVIRONMENT} from '../shared/shared';
import {Router} from "@angular/router";

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  public currentUser$: Observable<any>;
  private currentUserSubject: BehaviorSubject<any>;

  constructor(
    private httpClient: HttpClient,
    @Inject(ENVIRONMENT) protected env: any, // private appConfigService: AppConfigService
    private router: Router
  ) {
    // @ts-ignore
    this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser$ = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }


  login(form: any): any {
    return this.httpClient.post<any>('/login', form).pipe(tap((user) => {
      if (user && user.roles.length > 0) {
        const roles: any[] = []
        user.roles.forEach((item: any) => {
          roles.push(item.role)
        })
        const currentUser = {
          username: user.username,
          token: user.token,
          roles: roles,
        }
        localStorage.setItem('currentUser', JSON.stringify(currentUser));
      }
    }))
  }


  register(form: any): Observable<any> {
    return this.httpClient.post<any>('/register', form);
  }


  logout(error?: any) {
    try {
      localStorage.removeItem('currentUser');
      this.currentUserSubject.next(null);

      this.httpClient.post<any>('/logout', null);
      this.router.navigate(['/home']).then(() => {
        window.location.reload();
      });
      return true;
    } catch (err) {
      return false;
    }
  }
}
