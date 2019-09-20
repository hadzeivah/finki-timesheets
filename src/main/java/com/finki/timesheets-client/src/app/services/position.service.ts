import { Injectable } from '@angular/core';
import {AppConstants} from '../app.constants';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Position} from "../model/Position";

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

  findPositionByProject(id: number): Observable<Position[]> {
    return this.http.get<Position[]>(`${this.baseUrl}/project/${id}`);
  }

  findPositions(): Observable<Position[]> {
    return this.http.get<Position[]>(`${this.baseUrl}`);
  }
}
