import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Position} from "../model/Position";

@Injectable({
  providedIn: 'root'
})
export class PositionService {

  constructor(private http: HttpClient) {
  }

  findSalaryGroupedByPosition(id: number): Observable<Map<string,number>> {
    return this.http.get<Map<string, number>>(`/api/positions/salaries/map/project/${id}`);
  }
  findPositions(): Observable<Position[]> {
    return this.http.get<Position[]>(`/api/positions`);
  }

  findPositionByProjectPositionId(id: number): Observable<Position> {
    return this.http.get<Position>(`/api/positions/projectPosition/${id}`);
  }
}
