import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';

import {TableColumnInterface} from '../../shared/dummy-table';
import {CarRecord, CarRecordList} from "./car-record";
import {Status} from "../../shared/shared";

@Injectable({
  providedIn: 'root'
})
export class CarRecordService {
  private readonly endpoint = 'cars';

  public carRecordsListColumns: TableColumnInterface[] = [
    {
      id: '1',
      title: 'ID',
      propsName: 'id',
      width: '70px',
      type: 'text',
      sortFn: (a: any, b: any) => a.car_id - b.car_id,
      showSortFn: true
    },
    {
      id: '2',
      title: 'Registration number',
      propsName: 'registrationNumber',
      key: 'name',
      width: '110px',
      sortFn: (a: any, b: any) => a.regno.localeCompare(b.regno),
      showSortFn: true,
      type: 'text',
    },
    {
      id: '3',
      title: 'Brand',
      propsName: 'brand',
      width: '100px',
      sortFn: (a: any, b: any) => a.brand.localeCompare(b.brand),
      showSortFn: true,
      type: 'text',
    },
    {
      id: '4',
      title: 'Model',
      propsName: 'model',
      width: '100px',
      sortFn: (a: any, b: any) => a.model.localeCompare(b.model),
      showSortFn: true,
      type: 'text',
    },

    {
      id: '5',
      title: 'Owner',
      propsName: 'owner',
      width: '100px',
      sortFn: (a: any, b: any) => a.owner.localeCompare(b.owner),
      showSortFn: true,
      type: 'text',
    },
    {
      id: '5',
      title: 'Status',
      propsName: 'status',
      width: '100px',
      sortFn: (a: any, b: any) => Number(a.status) - Number(b.status),
      showSortFn: true,
      type: 'status',
    },

  ];

  public carRecords: CarRecordList[] = [
    {
      id: 1,
      registrationNumber: 'В0011ВВ',
      brand: 'Skoda',
      model: 'Octavia',
      owner: 'Firm1',
      status: Status.ACTIVE
    },
    {
      id: 2,
      registrationNumber: 'В9595СТ',
      brand: 'Dacia',
      model: 'Sandero',
      owner: 'Firm2',
      status: Status.ACTIVE
    },
    {
      id: 3,
      registrationNumber: 'В6632АС',
      brand: 'Ford',
      model: 'Transit',
      owner: 'Firm2',
      status: Status.ACTIVE
    },
    {
      id: 4,
      registrationNumber: 'В5215ТС',
      brand: 'Toyota',
      model: 'Hilux',
      owner: 'Firm3',
      status: Status.ACTIVE
    }
  ]

  constructor(private http: HttpClient) {
  }

  // columns
  public getColumns(): Observable<any> {
    return of(this.carRecordsListColumns);
  }

  // data
  // fetchLatest(): Observable<any> {
  //   return this.http.get<any>(this.endpoint);
  // }
  fetchLatest(): Observable<any> {
    return of(this.carRecords);
  }

  deleteRecord(id: number) {
    return this.http.delete<any>(`${this.endpoint}/${id}`);
  }

  fetchRecordById(id: any) {
    return this.http.get<CarRecord>(`${this.endpoint}/${id}`);
  }

  updateRecords(id:number, body: any) {
    return this.http.put(`${this.endpoint}/${id}`, body);
  }

  createRecords(body:any) {
    return this.http.post(`${this.endpoint}`, body);
  }
}

