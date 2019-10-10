import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";
import {Item} from "../model/Item";
import {University} from "../model/University";

@Injectable({
  providedIn: 'root'
})
export class UniversityService {

  constructor(private http: HttpClient) { }

  findUniversities(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`/api/universities`);
  }
  addUniversity(university: Item): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`/api/universities`, university);
  }
  deleteUniversity(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`/api/universities/${id}`);
  }
  updateUniversity(university: University): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`/api/universities/${university.id}`, university);
  }
}
