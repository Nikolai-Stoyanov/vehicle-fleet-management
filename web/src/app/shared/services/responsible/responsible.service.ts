import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {TableColumnInterface} from "../../dummy-table";
import {of} from "rxjs";



@Injectable({
  providedIn: 'root'
})
export class ResponsibleService {
  private readonly endpoint = 'cars';

  public carAccountableColumns: TableColumnInterface[] = [
    {
      id: '1',
      title: $localize`Id`,
      propsName: 'driverId',
      width: '80px',
      type: 'text',
    },
    {
      id: '2',
      title: $localize`Phone number`,
      propsName: 'phoneNumber',
      width: '120px',
      type: 'text',
    },
    {
      id: '3',
      title: $localize`Status`,
      propsName: 'Status',
      width: '60px',
      type: 'status',
    }
  ];

  constructor(private http: HttpClient) {
  }

  getColumns() {
    return of(this.carAccountableColumns);
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

