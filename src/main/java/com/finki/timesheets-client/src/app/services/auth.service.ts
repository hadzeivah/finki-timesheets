import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from "../model/User";

@Injectable()
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();

  }


  logout(): Observable<Object> {
    this.currentUserSubject.next(null);
    return this.http.post<Object>('/api/logout', null);
  }


  login(): Observable<User> {
    return this.http.get<User>('/api');
  }

  public isAuthenticated(): boolean {
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.currentUserSubject.next(currentUser);
    return currentUser != null;
  }
}
