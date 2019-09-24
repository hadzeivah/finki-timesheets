import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiResponse} from '../model/api.response';
import {AppConstants} from '../app.constants';
import {JwtHelperService} from '@auth0/angular-jwt';
import {User} from "../model/User";

@Injectable()
export class AuthService {

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) {
  }

  baseUrl = AppConstants.baseURL + 'users/';

  login(loginPayload): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(AppConstants.baseURL + 'token/generate-token', loginPayload);

  }

  static logout() {
    localStorage.removeItem("token");
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

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return !this.jwtHelper.isTokenExpired(token);
  }
}
