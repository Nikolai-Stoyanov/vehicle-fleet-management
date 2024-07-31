import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import {CarPerson} from './car-person';

@Injectable({
  providedIn: 'root'
})
export class CarPersonsService {
  private readonly endpoint = '/carPerson';

  public carPersonListColumns  = [
    {
      id: '1',
      title: $localize`ID`,
      propsName: 'id',
      width: '70px',
      type: 'text',
      visible: true,
      align: 'center',
      sortFn: (a: any, b: any) => a.id - b.id,
      showSortFn: true
    },
    {
      id: '2',
      title: $localize`First name`,
      propsName: 'firstName',
      width: '100px',
      sortFn: (a: any, b: any) => a.firstName.localeCompare(b.firstName),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },
    {
      id: '3',
      title: $localize`Last name`,
      propsName: 'lastName',
      width: '200px',
      sortFn: (a: any, b: any) => a.lastName.localeCompare(b.lastName),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },
    {
      id: '4',
      title: $localize`Phone number`,
      propsName: 'phoneNumber',
      width: '130px',
      sortFn: (a: any, b: any) =>
        a.phoneNumber.localeCompare(b.phoneNumber),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },

    {
      id: '6',
      title: $localize`Status`,
      propsName: 'status',
      width: '100px',
      sortFn: (a: any, b: any) => Number(a.status) - Number(b.status),
      showSortFn: true,
      type: 'status',
      visible: true,
      align: 'center',
      right: true
    }

  ];

  constructor(private http: HttpClient) {}
  // columns
  public getColumns(): Observable<any> {
    return of(this.carPersonListColumns);
  }

  // data

  fetchLatest(): Observable<any> {
    return this.http.get<any>(`${this.endpoint}`)
  }

  fetchPersonById(personId: number): Observable<any> {
    return this.http.get<any>(`${this.endpoint}/${personId}`)
  }

  public deletePerson( personId: number): Observable<any> {
    return this.http.delete<any>(`${this.endpoint}/${personId}`);
  }

  public createPerson(model: CarPerson): Observable<any> {
    return this.http.post<any>(`${this.endpoint}`,model)
  }
  public updatePerson(personId:number,model: CarPerson): Observable<any> {
    return this.http.put<any>(`${this.endpoint}/${personId}`,model)
  }
}
