import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HolidayService {

  constructor(private http: HttpClient) { }

  findHolidays(): Observable<string[]> {
    return this.http.get<string[]>(`/api/holidays`);
  }
}
