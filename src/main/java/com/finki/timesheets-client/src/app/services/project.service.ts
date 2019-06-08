import { Injectable } from '@angular/core';
import {AppConstants} from '../app.constants';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Item} from '../model/Item';
import {Project} from '../model/Project';
import {ApiResponse} from '../model/api.response';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  baseUrl = AppConstants.baseURL + 'projects';

  constructor(private http: HttpClient) {
  }

  findProjects(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }
}
