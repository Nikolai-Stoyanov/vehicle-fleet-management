import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { DeclarationList } from './declarations';
import {CarRecord} from "../car-records/car-record";

@Injectable({
  providedIn: 'root'
})
export class DeclarationsService {
  private readonly endpoint = '';

  public declarationListColumns  = [
    {
      id: '1',
      title: 'ID',
      propsName: 'id',
      width: '70px',
      type: 'text',
      align: 'center',
      sortFn: (a: any, b: any) => a.id - b.id,
      showSortFn: true
    },
    {
      id: '2',
      title: 'Period',
      propsName: 'period',
      width: '100px',
      sortFn: (a: any, b: any) => a.period.localeCompare(b.period),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },
    {
      id: '3',
      title: 'Responsible',
      propsName: 'responsible',
      width: '100px',
      sortFn: (a: any, b: any) => a.responsible.localeCompare(b.responsible),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },
    {
      id: '4',
      title: 'registrationNumber',
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
      title: 'Status',
      propsName: 'status',
      width: '100px',
      sortFn: (a: any, b: any) => Number(a.status) - Number(b.status),
      showSortFn: true,
      type: 'status',
      align: 'center',
      right: true
    }

  ];

  public declarationListData: DeclarationList[] = [
    {
      id: 1,
      period: '2020-01',
      responsible: 'Petar',
      registrationNumber: 'C0505TT',
      status: true,
      carId:5
    },
    {
      id: 2,
      period: '2023-06',
      responsible: 'Ivan',
      registrationNumber: 'CC0654BB',
      status: true,
      carId:2
    },
    {
      id: 3,
      period: '2024-04',
      responsible: 'Maria',
      registrationNumber: 'CT2265AA',
      status: true,
      carId:3
    },
    {
      id: 4,
      period: '2024-02',
      responsible: 'Petar',
      registrationNumber: 'C0505TT',
      status: true,
      carId:5
    },
    {
      id: 5,
      period: '2020-03',
      responsible: 'Petar',
      registrationNumber: 'C0505TT',
      status: true,
      carId:5
    },
    {
      id: 6,
      period: '2020-01',
      responsible: 'Gosho',
      registrationNumber: 'B8874BB',
      status: true,
      carId:6
    },
  ];

  constructor(private http: HttpClient) {}
  public getColumns(): Observable<any> {
    return of(this.declarationListColumns);
  }

  fetchLatest(): Observable<any> {
    return of(this.declarationListData);
  }

  public deleteDeclaration( brandId: number): Observable<any> {
    return this.http.delete<any>(`${this.endpoint}/${brandId}`);
  }
  // fetchLatest(): Observable<any> {
  //   return this.http.get<any>(this.endpoint);
  // }

  fetchDeclarationById(id: any) {
    return this.http.get<CarRecord>(`${this.endpoint}/${id}`);
  }
}
