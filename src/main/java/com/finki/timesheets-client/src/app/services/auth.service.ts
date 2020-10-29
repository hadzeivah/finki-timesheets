import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {ApiResponse} from '../model/api.response';
import {User} from "../model/User";
import {map} from "rxjs/operators";
import decode from 'jwt-decode';

@Injectable()
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();

  }

  login(loginPayload) {
    return this.http.post<ApiResponse>('/token/generate-token', loginPayload)
      .pipe(map(user => {
        // login successful if there's a jwt token in the response
        if (user.result && user.result.token) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes

          user.result.role = decode(user.result.token).scopes;
          localStorage.setItem('currentUser', JSON.stringify(user.result));
          this.currentUserSubject.next(user.result);
        }

        return user;
      }));
  }

  logout() {
    localStorage.removeItem("currentUser");
    this.currentUserSubject.next(null);
  }

  getUsers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>('/api/users');
  }

  getLoggedUser(): Observable<User> {
    return this.http.get<User>('/api/loggedUser');
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
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.currentUserSubject.next(currentUser);
    return currentUser != null;
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }
}
