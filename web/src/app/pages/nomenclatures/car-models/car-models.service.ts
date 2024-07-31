import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { CarModel } from './car-model';

@Injectable({
  providedIn: 'root'
})
export class CarModelsService {
  private readonly endpoint = '/carModel';

  public carBrandsListColumns  = [
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
      title: $localize`Name`,
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
      title: $localize`Description`,
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
      title: $localize`Brand`,
      propsName: 'brandName',
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
      title: $localize`Year`,
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
    return this.http.get<any>(`${this.endpoint}`)
  }

  fetchModelById(modelId: number): Observable<any> {
    return this.http.get<any>(`${this.endpoint}/${modelId}`)
  }

  public deleteModel( modelId: number): Observable<any> {
    return this.http.delete<any>(`${this.endpoint}/${modelId}`);
  }

  public createModel(model: CarModel): Observable<any> {
    return this.http.post<any>(`${this.endpoint}`,model)
  }
  public updateModel(modelId:number,model: CarModel): Observable<any> {
    return this.http.put<any>(`${this.endpoint}/${modelId}`,model)
  }
}
