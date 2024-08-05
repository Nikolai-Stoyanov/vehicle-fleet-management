import {Injectable, Inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {BehaviorSubject, Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

import {ENVIRONMENT} from '../shared/shared';
import {Router} from "@angular/router";

import {decodeJwt} from 'jose';

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  public currentUser$: Observable<any>;
  private currentUserSubject: BehaviorSubject<any>;

  constructor(
    private httpClient: HttpClient,
    @Inject(ENVIRONMENT) protected env: any,
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


  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/home']).then(() => {
      window.location.reload();
    });
    return true

  }

  isTokenValid() {
    // @ts-ignore
    const token = JSON.parse(localStorage.getItem('currentUser'))?.token;
    if (token) {
      let claims;
      try {
        claims = decodeJwt(token)
      } catch (error) {
        console.log(error);
      }
      const now = Date.now()
      if (now < claims.exp) {
        return;
      }
    }
    this.logout()
  }
}
