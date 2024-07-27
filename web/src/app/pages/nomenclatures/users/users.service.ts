import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { Users } from './users';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private readonly endpoint = '/users';

  public userListColumns  = [
    {
      id: '1',
      title: $localize`ID`,
      propsName: 'id',
      width: '70px',
      type: 'text',
      align: 'center',
      sortFn: (a: any, b: any) => a.id - b.id,
      showSortFn: true
    },
    {
      id: '2',
      title: $localize`Name`,
      propsName: 'username',
      width: '130px',
      sortFn: (a: any, b: any) => a.username.localeCompare(b.username),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },
    {
      id: '3',
      title: $localize`Email`,
      propsName: 'email',
      width: '130px',
      sortFn: (a: any, b: any) => a.email.localeCompare(b.email),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },
    {
      id: '4',
      title: $localize`Roles`,
      propsName: 'roles',
      width: '300px',
      type: 'array',
      align: 'center',
      right: true
    }

  ];
  constructor(private http: HttpClient) {}
  // columns
  public getColumns(): Observable<any> {
    return of(this.userListColumns);
  }

  // data

  fetchLatest(): Observable<any> {
    return this.http.get<any>(`${this.endpoint}`)
  }

  fetchUserById(userId: number): Observable<any> {
    return this.http.get<any>(`${this.endpoint}/${userId}`)
  }

  public deleteUser( userId: number): Observable<any> {
    return this.http.delete<any>(`${this.endpoint}/${userId}`);
  }

  public updateUser(userId:number,user: Users): Observable<any> {
    return this.http.put<any>(`${this.endpoint}/${userId}`,user)
  }

 public userRoleListData(): Observable<any>  {
   return this.http.get<any>(`${this.endpoint}/roles`)
  }
}
