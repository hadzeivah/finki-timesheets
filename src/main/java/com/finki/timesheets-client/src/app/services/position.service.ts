import { Injectable } from '@angular/core';
import {AppConstants} from '../app.constants';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PositionService {
  baseUrl = AppConstants.baseURL + 'positions';

  constructor(private http: HttpClient) {
  }

  findSalaryGroupedByPosition(): Observable<Map<string,number>> {
    return this.http.get<Map<string,number>>(`${this.baseUrl}/salary`);
  }
}
