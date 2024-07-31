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
  private readonly endpoint = '/carRecord';

  public carRecordsListColumns: TableColumnInterface[] = [
    {
      id: '1',
      title: $localize`ID`,
      propsName: 'id',
      width: '70px',
      type: 'text',
      sortFn: (a: any, b: any) => a.car_id - b.car_id,
      showSortFn: true
    },
    {
      id: '2',
      title: $localize`Registration number`,
      propsName: 'registrationNumber',
      key: 'name',
      width: '110px',
      sortFn: (a: any, b: any) => a.regno.localeCompare(b.regno),
      showSortFn: true,
      type: 'text',
    },
    {
      id: '3',
      title: $localize`Brand`,
      propsName: 'carBrand',
      width: '100px',
      sortFn: (a: any, b: any) => a.brand.localeCompare(b.brand),
      showSortFn: true,
      type: 'text',
    },
    {
      id: '4',
      title: $localize`Model`,
      propsName: 'carModel',
      width: '100px',
      sortFn: (a: any, b: any) => a.model.localeCompare(b.model),
      showSortFn: true,
      type: 'text',
    },
    {
      id: '5',
      title: $localize`Owner`,
      propsName: 'owner',
      width: '100px',
      sortFn: (a: any, b: any) => a.owner.localeCompare(b.owner),
      showSortFn: true,
      type: 'text',
    },
    {
      id: '5',
      title: $localize`Status`,
      propsName: 'status',
      width: '100px',
      sortFn: (a: any, b: any) => Number(a.status) - Number(b.status),
      showSortFn: true,
      type: 'status',
    },

  ];

  constructor(private http: HttpClient) {
  }

  // columns
  public getColumns(): Observable<any> {
    return of(this.carRecordsListColumns);
  }

  // data

  fetchLatestRecords() {
    return this.http.get<any>(`${this.endpoint}`);
  }

  deleteRecord(id: number) {
    return this.http.delete<any>(`${this.endpoint}/${id}`);
  }

  fetchRecordById(id: any) {
    return this.http.get<CarRecord>(`${this.endpoint}/${id}`);
  }

  updateRecords(id:any, body: any) {
    return this.http.put(`${this.endpoint}/${id}`, body);
  }

  createRecords(body:any) {
    return this.http.post(`${this.endpoint}`, body);
  }

  fetchAllRegistrationNumbers() {
    return this.http.get<any>(`${this.endpoint}/registrationNumber`);
  }

  getCarRecordInfo(id:any) {
    return this.http.get<any>(`${this.endpoint}/registrationCertificateData/${id}`);
  }
}

