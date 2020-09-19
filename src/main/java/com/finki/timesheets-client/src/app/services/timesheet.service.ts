import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Timesheet} from '../model/Timesheet';
import {ApiResponse} from "../model/api.response";


@Injectable({
  providedIn: 'root'
})
export class TimesheetService {

  constructor(private http: HttpClient) {
  }

  findWorkingHoursSummaryByMember(memberId: number): Observable<any> {
    return this.http.get<any>(`/api/timesheets/working_hours_summary/member/${memberId}`)
  }

  findTimesheet(projectId: number, memberId: number): Observable<Timesheet> {
    return this.http.get<Timesheet>('/api/timesheets', {
      params: new HttpParams()
        .set('projectId', projectId.toString())
        .set('memberId', memberId.toString())
    });
  }

  deleteTimesheet(memberId: number, projectId: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`/api/timesheets/member/${memberId}/project/${projectId}`)
  }
}


