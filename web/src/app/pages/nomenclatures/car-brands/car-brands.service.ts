import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { CarBrand } from './car-brands';

@Injectable({
  providedIn: 'root'
})
export class CarBrandsService {
  private readonly endpoint = '/carBrand';

  public carBrandsListColumns  = [
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
      propsName: 'name',
      width: '100px',
      sortFn: (a: any, b: any) => a.name.localeCompare(b.name),
      showSortFn: true,
      type: 'text',
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
      align: 'center'
    },
    {
      id: '4',
      title: $localize`Company`,
      propsName: 'company',
      width: '130px',
      sortFn: (a: any, b: any) =>
        a.company.localeCompare(b.company),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },
    {
      id: '5',
      title: $localize`Models`,
      propsName: 'models',
      width: '200px',
      showSortFn: false,
      type: 'array',
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
      align: 'center',
    }

  ];

  constructor(private http: HttpClient) {}
  // columns
  public getColumns(): Observable<any> {
    return of(this.carBrandsListColumns);
  }

  // data

  fetchLatest(filter:string=''): Observable<any> {
    return this.http.get<any>(`${this.endpoint}?filter=${filter}`)
  }

  fetchBrandById(brandId: number): Observable<any> {
    return this.http.get<any>(`${this.endpoint}/${brandId}`)
  }

  public deleteBrand( brandId: number): Observable<any> {
    return this.http.delete<any>(`${this.endpoint}/${brandId}`);
  }

  public createBrand(brand: CarBrand): Observable<any> {
    return this.http.post<any>(`${this.endpoint}`,brand)
  }
  public updateBrand(brandId:number,brand: CarBrand): Observable<any> {
    return this.http.put<any>(`${this.endpoint}/${brandId}`,brand)
  }
}
