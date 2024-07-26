import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import {FuelProviderType, FuelType} from './fuel';
import { TableColumnInterface } from '../../../shared/dummy-table';

@Injectable()
export class FuelService {
  public fuelProviderColumns: TableColumnInterface[] = [
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
      type: 'text',
      align: 'left',
      sortFn: (a: any, b: any) => a.name.localeCompare(b.name),
      showSortFn: true
    },
    {
      id: '3',
      title: $localize`Description`,
      propsName: 'description',
      width: '200px',
      type: 'text',
      align: 'left',
      sortFn: (a: any, b: any) => a.description.localeCompare(b.description),
      showSortFn: true
    },
    {
      id: '4',
      title: $localize`Fuels`,
      propsName: 'fuelList',
      width: '200px',
      type: 'array',
      align: 'center'
    },
    {
      id: '5',
      title: $localize`Status`,
      propsName: 'status',
      width: '150px',
      sortFn: (a: any, b: any) => Number(a.active) - Number(b.active),
      showSortFn: true,
      type: 'status',
      align: 'center',
    }
  ];
  public fuelColumns: TableColumnInterface[] = [
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
      width: '150px',
      type: 'text',
      align: 'left',
      sortFn: (a: any, b: any) => a.name.localeCompare(b.name),
      showSortFn: true
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
      title: $localize`Status`,
      propsName: 'status',
      width: '100px',
      sortFn: (a: any, b: any) => Number(a.status) - Number(b.status),
      showSortFn: true,
      type: 'status',
      align: 'center',
    }
  ];
  private readonly fuelEndpoint = '/fuel';
  private readonly supplierEndpoint = '/fuelSupplier';

  constructor(private http: HttpClient) {}

  public getFuelColumns(): Observable<any> {
    return of(this.fuelColumns);
  }

  public getFuelProviderColumns(): Observable<any> {
    return of(this.fuelProviderColumns);
  }

  fetchLatestFuels(): Observable<any> {
    return this.http.get<any>(`${this.fuelEndpoint}`)
  }
  fetchLatestSuppliers(): Observable<any> {
    return this.http.get<any>(`${this.supplierEndpoint}`)
  }

  fetchFuelById(fuelId: number): Observable<any> {
    return this.http.get<any>(`${this.fuelEndpoint}/${fuelId}`)
  }

  fetchSupplierById(supplierId: number): Observable<any> {
    return this.http.get<any>(`${this.supplierEndpoint}/${supplierId}`)
  }

  public deleteFuel( fuelId: number): Observable<any> {
    return this.http.delete<any>(`${this.fuelEndpoint}/${fuelId}`);
  }

  public deleteSupplier( supplierId: number): Observable<any> {
    return this.http.delete<any>(`${this.supplierEndpoint}/${supplierId}`);
  }

  public createFuel(fuel: FuelType): Observable<any> {
    return this.http.post<any>(`${this.fuelEndpoint}`,fuel)
  }

  public createSupplier(supplier: FuelProviderType): Observable<any> {
    return this.http.post<any>(`${this.supplierEndpoint}`,supplier)
  }

  public updateFuel(fuelId:number,fuel: FuelProviderType): Observable<any> {
    return this.http.put<any>(`${this.fuelEndpoint}/${fuelId}`,fuel)
  }

  public updateSupplier(supplierId:number,supplier: FuelProviderType): Observable<any> {
    return this.http.put<any>(`${this.supplierEndpoint}/${supplierId}`,supplier)
  }
}
