import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppConstants} from "../app.constants";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HolidayService {

  baseUrl = AppConstants.baseURL + 'holidays';

  constructor(private http: HttpClient) { }

  findHolidays(): Observable<string[]> {
    return this.http.get<string[]>(this.baseUrl);
  }
}
