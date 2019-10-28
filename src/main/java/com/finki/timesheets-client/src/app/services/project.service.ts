import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiResponse} from '../model/api.response';
import {ProjectPositionsDto} from "../model/ProjectPositionsDto";
import {ProjectMemberDto} from "../model/ProjectMemberDto";
import {Project} from "../model/Project";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient) {
  }

  findProjects(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`/api/projects`);
  }

  findUnapprovedProjects(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`/api/projects/unapproved`);
  }

  findProjectById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`/api/projects/${id}`);
  }

  assignMemberToProject(projectMemberDto: ProjectMemberDto): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`/api/projects/assignMember`, projectMemberDto);
  }

  addProject(projectDto: ProjectPositionsDto): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`/api/projects`, projectDto);
  }

  deleteProject(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`/api/projects/${id}`);
  }

  updateProject(projectDto: ProjectPositionsDto): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`/api/projects/${projectDto.project.id}`, projectDto);
  }

  approveProject(project: Project): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`/api/projects/${project.id}/approve`, null);
  }

}
