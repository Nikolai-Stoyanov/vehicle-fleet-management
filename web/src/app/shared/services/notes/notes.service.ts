import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';



@Injectable({
  providedIn: 'root'
})
export class NotesService {
  private readonly endpoint = 'cars';


  constructor(private http: HttpClient) {
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

