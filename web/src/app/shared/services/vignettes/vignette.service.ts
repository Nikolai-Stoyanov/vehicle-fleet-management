import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {TableColumnInterface} from "../../dummy-table";



@Injectable({
  providedIn: 'root'
})
export class VignetteService {
  private readonly endpoint = 'cars';

  public carVignetteColumns: TableColumnInterface[] = [
    {
      id: '1',
      title: 'Vignette type',
      propsName: 'vignetteType',
      width: '150px',
      type: 'text',
      visible: true
    },
    {
      id: '2',
      title: 'Date from',
      propsName: 'dateFrom',
      width: '150px',
      type: 'date',
    },
    {
      id: '3',
      title: 'Date to',
      propsName: 'dateTo',
      width: '150px',
      type: 'date',
    },
    {
      id: '4',
      title: 'Description',
      propsName: 'description',
      width: '150px',
      type: 'text',
    },
    {
      id: '5',
      title: 'Created by',
      propsName: 'createdBy',
      width: '150px',
      type: 'text',
    },
    {
      id: '6',
      title: 'Created date',
      propsName: 'createdDate',
      width: '150px',
      type: 'date',
    },
    {
      id: '7',
      title: 'Modify by',
      propsName: 'modifyBy',
      width: '150px',
      type: 'text',
    },
    {
      id: '8',
      title: 'Date to',
      propsName: 'dateTo',
      width: '150px',
      type: 'date',
    },
  ];

  constructor(private http: HttpClient) {
  }

  public getColumns(): Observable<any> {
    return of(this.carVignetteColumns);
  }

  fetchLatest(carId: number) {
    return this.http.get<any>(`${this.endpoint}/${carId}`);
  }

  delete(carId: number) {
    return this.http.delete<any>(`${this.endpoint}/${carId}`)
  }

  update(id:number, body: any) {
    return this.http.put(`${this.endpoint}/${id}`, body);
  }

  create(body:any) {
    return this.http.post(`${this.endpoint}`, body);
  }
}

