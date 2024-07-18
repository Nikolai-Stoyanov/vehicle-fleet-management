import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {of} from "rxjs";
import {TableColumnInterface} from "../../dummy-table";



@Injectable({
  providedIn: 'root'
})
export class InspectionService {
  private readonly endpoint = 'cars';
  public carInspectionColumns: TableColumnInterface[] = [

    {
      id: '1',
      title: 'Date from',
      propsName: 'dateFrom',
      width: '150px',
      type: 'date',
    },
    {
      id: '2',
      title: 'Date to',
      propsName: 'dateTo',
      width: '150px',
      type: 'date',
    },
    {
      id: '3',
      title: 'Description',
      propsName: 'description',
      width: '150px',
      type: 'text',
    },
    {
      id: '4',
      title: 'Created by',
      propsName: 'createdBy',
      width: '150px',
      type: 'text',
    },
    {
      id: '5',
      title: 'Created date',
      propsName: 'createdDate',
      width: '150px',
      type: 'date',
    },
    {
      id: '6',
      title: 'Modify by',
      propsName: 'modifyBy',
      width: '150px',
      type: 'text',
    },
    {
      id: '7',
      title: 'Date to',
      propsName: 'dateTo',
      width: '150px',
      type: 'date',
    },
  ];

  constructor(private http: HttpClient) {
  }

  getColumns() {
    return of(this.carInspectionColumns);
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

