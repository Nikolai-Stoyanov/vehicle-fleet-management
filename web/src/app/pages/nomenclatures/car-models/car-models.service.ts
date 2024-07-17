import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { CarModel } from './car-model';

@Injectable({
  providedIn: 'root'
})
export class CarModelsService {
  private readonly endpoint = '';

  public carBrandsListColumns  = [
    {
      id: '1',
      title: 'ID',
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
      title: 'Name',
      propsName: 'name',
      width: '100px',
      sortFn: (a: any, b: any) => a.name.localeCompare(b.name),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },
    {
      id: '3',
      title: 'Description',
      propsName: 'description',
      width: '200px',
      sortFn: (a: any, b: any) => a.description.localeCompare(b.description),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },
    {
      id: '4',
      title: 'Brand',
      propsName: 'brand',
      width: '130px',
      sortFn: (a: any, b: any) =>
        a.brand.localeCompare(b.brand),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },
    {
      id: '5',
      title: 'Year',
      propsName: 'year',
      width: '200px',
      sortFn: (a: any, b: any) =>
        a.year.localeCompare(b.year),
      showSortFn: true,
      type: 'date',
      visible: true,
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
      visible: true,
      align: 'center',
      right: true
    }

  ];

  public carModelsListData: CarModel[] = [
    {
      id: 1,
      name: 'Octavia',
      description: '',
      brand: 1,
      year: '01-01-2020',
      status: true
    },
    {
      id: 2,
      name: 'Ranger',
      description: '',
      brand: 2,
      year: '01-01-2022',
      status: true
    },
    {
      id: 3,
      name: 'Duster',
      description: '',
      brand: 3,
      year: '01-01-2015',
      status: true
    },
    {
      id: 4,
      name: 'Dokker',
      description: '',
      brand: 3,
      year: '01-07-2018',
      status: true
    },
    {
      id: 5,
      name: 'Logan',
      description: '',
      brand: 3,
      year: '01-07-2021',
      status: true
    },
    {
      id: 6,
      name: 'Hilux',
      description: '',
      brand: 4,
      year: '01-09-2023',
      status: true
    },
    {
      id: 7,
      name: 'Fabia',
      description: '',
      brand: 1,
      year: '01-07-2019',
      status: true
    },

  ];

  constructor(private http: HttpClient) {}
  // columns
  public getColumns(): Observable<any> {
    return of(this.carBrandsListColumns);
  }

  // data

  fetchLatest(): Observable<any> {
    return of(this.carModelsListData);
  }

  public deleteModel( brandId: number): Observable<any> {
    return this.http.delete<any>(`${this.endpoint}/${brandId}`);
  }
  // fetchLatest(): Observable<any> {
  //   return this.http.get<any>(this.endpoint);
  // }
}
