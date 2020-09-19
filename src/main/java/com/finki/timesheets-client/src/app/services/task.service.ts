import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient) {
  }

  deleteTask(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`/api/work_packages/tasks/${id}`);
  }

}
