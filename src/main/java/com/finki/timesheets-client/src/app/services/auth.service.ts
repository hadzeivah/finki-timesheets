import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiResponse} from '../model/api.response';
import {JwtHelperService} from '@auth0/angular-jwt';
import {User} from "../model/User";

@Injectable()
export class AuthService {

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) {
  }

  login(loginPayload): Observable<ApiResponse> {
    return this.http.post<ApiResponse>('/api/token/generate-token', loginPayload);

  }

  static logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
  }

  getUsers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>('/api/users');
  }

  getUserById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`/api/users/${id}`);
  }

  createUser(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>('/api/users', user);
  }

  updateUser(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`/api/users/${user.id}`, user);
  }

  deleteUser(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`/api/users/${id}`);
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return !this.jwtHelper.isTokenExpired(token);
  }

  public getLoggedUser(): string {
    return localStorage.getItem("username");
  }
}
