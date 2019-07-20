import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppConstants} from "../app.constants";
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";
import {Item} from "../model/Item";
import {University} from "../model/University";

@Injectable({
  providedIn: 'root'
})
export class UniversityService {

  baseUrl = AppConstants.baseURL + 'universities';

  constructor(private http: HttpClient) { }

  findUniversities(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }
  addUniversity(university: Item): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, university);
  }
  deleteUniversity(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + '/' + id);
  }
  updateUniversity(university: University): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl +  '/' + university.id, university);
  }
}
