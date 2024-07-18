import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {of} from "rxjs";
import {TableColumnInterface} from "../../dummy-table";



@Injectable({
  providedIn: 'root'
})
export class RegistrationNumberService {
  private readonly endpoint = 'cars';

  public carRegistrationNumbersColumns: TableColumnInterface[] = [
    {
      id: '1',
      title: 'Registration',
      propsName: 'registration',
      width: '150px',
      type: 'text',
    },
    {
      id: '2',
      title: 'Registration date',
      propsName: 'registrationDate',
      width: '150px',
      type: 'date',
    },
    {
      id: '3',
      title: 'Status',
      propsName: 'status',
      width: '150px',
      type: 'status',
    },
  ];

  constructor(private http: HttpClient) {
  }

  getColumns() {
    return of(this.carRegistrationNumbersColumns);
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

