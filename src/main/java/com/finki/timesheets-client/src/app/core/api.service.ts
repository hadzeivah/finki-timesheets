import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from '../model/user.model';
import {Observable, of} from 'rxjs';
import {ApiResponse} from '../model/api.response';
import {AppConstants} from '../app.constants';

@Injectable()
export class ApiService {

  constructor(private http: HttpClient) {
  }

  baseUrl = AppConstants.baseURL + 'users';

  login(loginPayload): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(AppConstants.baseURL + 'token/generate-token', loginPayload);
  }

  getUsers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }

  getUserById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + id);
  }

  createUser(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, user);
  }

  updateUser(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + user.id, user);
  }

  deleteUser(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id);
  }
}
