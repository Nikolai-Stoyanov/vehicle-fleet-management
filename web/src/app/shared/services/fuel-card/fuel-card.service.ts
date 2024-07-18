import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {of} from "rxjs";
import {TableColumnInterface} from "../../dummy-table";

@Injectable({
  providedIn: 'root'
})
export class FuelCardService {
  private readonly endpoint = 'cars';

  public carCardColumns: TableColumnInterface[] = [
    {
      id: '1',
      title: 'Card number',
      propsName: 'cardNumber',
      width: '150px',
      type: 'text',
    },
    {
      id: '2',
      title: 'Limit',
      propsName: 'cardLimit',
      width: '90px',
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

  getColumns() {
    return of(this.carCardColumns);
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

