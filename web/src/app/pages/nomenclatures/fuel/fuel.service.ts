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
      title: 'Name',
      propsName: 'name',
      width: '100px',
      type: 'text',
      align: 'left',
      sortFn: (a: any, b: any) => a.name.localeCompare(b.name),
      showSortFn: true
    },
    {
      id: '3',
      title: 'Description',
      propsName: 'description',
      width: '200px',
      type: 'text',
      align: 'left',
      sortFn: (a: any, b: any) => a.description.localeCompare(b.description),
      showSortFn: true
    },
    {
      id: '4',
      title: 'Fuels',
      propsName: 'fuelList',
      width: '200px',
      type: 'array',
      align: 'center'
    },
    {
      id: '5',
      title: 'Status',
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
      title: 'Name',
      propsName: 'name',
      width: '150px',
      type: 'text',
      align: 'left',
      sortFn: (a: any, b: any) => a.name.localeCompare(b.name),
      showSortFn: true
    },
    {
      id: '3',
      title: 'Description',
      propsName: 'description',
      width: '200px',
      sortFn: (a: any, b: any) => a.description.localeCompare(b.description),
      showSortFn: true,
      type: 'text',
      align: 'center'
    },
    {
      id: '4',
      title: 'Status',
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

  public fetchLatestFuel(): Observable<FuelType[]> {
    return of([
      {
        id: 1,
        name: 'Дизел',
        description:'',
        status: true
      },
      {
        id: 2,
        name: 'Бензин А95 Н',
        description: '',
        status: true
      },
      {
        id: 3,
        name: 'Газ',
        description: '',
        status: true
      },
      {
        id: 4,
        name: 'Бензин А98',
        description: '',
        status: true
      },
      {
        id: 5,
        name: 'Super Diesel',
        description: '',
        status: true
      },
      {
        id: 6,
        name: 'Metan',
        description: '',
        status: true
      }
    ]);
  }
  public fetchLatestProvider(): Observable<FuelProviderType[]> {
    return of([
      {
        id: 0,
        name: 'Петрол',
        description: 'petrol',
        fuelOptions: [
          { id: 1,price:1.5 },
          { id: 2, price:1.2  },
          { id: 3, price:0.9  }
        ],
        status: true
      },
      {
        id: 1,
        name: 'OMV',
        description: 'omv',
        fuelOptions: [
          { id: 1,price:1.6 },
          { id: 2, price:1.3  },
          { id: 3, price:0.8  }
        ],
        status: true
      },
      {
        id: 2,
        name: 'Лукойл',
        description: 'lucoil',
        fuelOptions: [
          { id: 1,price:1.2  },
          { id: 2, price:1.76},
          { id: 3, price:0.6}
        ],
        status: true
      },
      {
        id: 3,
        name: 'Бентойл',
        description: 'bentoil',
        fuelOptions: [
          { id: 1,price:1.5 },
          { id: 2, price:1.2 },
          { id: 3, price:0.9}
        ],
        status: true
      },
      {
        id: 4,
        name: 'Shell',
        description: 'shell',
        fuelOptions: [
          { id: 1,price:1.46 },
          { id: 2, price:1.66  },
          { id: 3, price:0.95 }
        ],
        status: true
      }
    ]);
  }

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
