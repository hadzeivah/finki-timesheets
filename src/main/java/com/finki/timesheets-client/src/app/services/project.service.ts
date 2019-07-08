import { Injectable } from '@angular/core';
import {AppConstants} from '../app.constants';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiResponse} from '../model/api.response';
import {Project} from "../model/Project";

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
  addProject(project: Project): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, project);
  }
  deleteProject(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + '/' + id);
  }
  updateProject(project: Project): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl +  '/' + project.id, project);
  }
}
