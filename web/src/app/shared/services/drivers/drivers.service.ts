import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {TableColumnInterface} from "../../dummy-table";
import {Observable, of} from "rxjs";



@Injectable({
  providedIn: 'root'
})
export class DriversService {
  private readonly endpoint = 'cars';

  public carDriversColumns: TableColumnInterface[] = [
    {
      id: '1',
      title: 'Id',
      propsName: 'driverId',
      width: '80px',
      type: 'text',
    },
    {
      id: '2',
      title: 'Phone number',
      propsName: 'phoneNumber',
      width: '120px',
      type: 'text',
    },
    {
      id: '3',
      title: 'Status',
      propsName: 'Status',
      width: '60px',
      type: 'status',
    }
  ];

  constructor(private http: HttpClient) {
  }

  public getColumns(): Observable<any> {
    return of(this.carDriversColumns);
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

