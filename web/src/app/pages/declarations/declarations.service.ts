import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { DeclarationList } from './declarations';
import {CarRecord} from "../car-records/car-record";

@Injectable({
  providedIn: 'root'
})
export class DeclarationsService {
  private readonly endpoint = '/declaration';

  public declarationListColumns  = [
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
      title: $localize`Period`,
      propsName: 'period',
      width: '100px',
      sortFn: (a: any, b: any) => a.period.localeCompare(b.period),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },
    {
      id: '3',
      title: $localize`Date from`,
      propsName: 'date',
      width: '100px',
      sortFn: (a: any, b: any) => a.date.localeCompare(b.date),
      showSortFn: true,
      type: 'date',
      align: 'center'
    },
    {
      id: '4',
      title: $localize`Responsible`,
      propsName: 'responsible',
      width: '100px',
      sortFn: (a: any, b: any) => a.responsible.localeCompare(b.responsible),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },
    {
      id: '5',
      title: $localize`Registration number`,
      propsName: 'registrationNumber',
      width: '100px',
      sortFn: (a: any, b: any) =>
        a.registrationNumber.localeCompare(b.registrationNumber),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },
    {
      id: '6',
      title: $localize`Driver`,
      propsName: 'driver',
      width: '100px',
      sortFn: (a: any, b: any) =>
        a.driver.localeCompare(b.driver),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },

  ];


  constructor(private http: HttpClient) {}

  public getColumns(): Observable<any> {
    return of(this.declarationListColumns);
  }


  fetchLatestDeclarations() {
    return this.http.get<any>(`${this.endpoint}`);
  }

  deleteDeclaration(id: number) {
    return this.http.delete<any>(`${this.endpoint}/${id}`);
  }

  fetchDeclarationById(id: any) {
    return this.http.get<CarRecord>(`${this.endpoint}/${id}`);
  }

  updateDeclaration(id:any, body: any) {
    return this.http.put(`${this.endpoint}/${id}`, body);
  }

  createDeclaration(body:any) {
    return this.http.post(`${this.endpoint}`, body);
  }
}
