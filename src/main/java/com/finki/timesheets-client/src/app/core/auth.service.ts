import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/user.model';
import {Observable} from 'rxjs';
import {ApiResponse} from '../model/api.response';
import {AppConstants} from '../app.constants';
import {tap} from "rxjs/operators";
import * as moment from "moment";

@Injectable()
export class AuthService {

  constructor(private http: HttpClient) {
  }

  baseUrl = AppConstants.baseURL + 'users/';

  login(loginPayload): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(AppConstants.baseURL + 'token/generate-token', loginPayload)
      .pipe(tap(res => this.setSession(res)))

  }

  static logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("expires_at");
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

  private setSession(authResult) {
    const expiresAt = moment().add(authResult.expiresIn, 'second');
    localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()));
  }

  public isLoggedIn() {
    return this.getExpiration().isValid() || moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }
}
