import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Timesheet} from '../model/Timesheet';
import {AppConstants} from '../app.constants';
import {catchError} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class TimesheetService {

  baseUrl = AppConstants.baseURL + 'timesheets';

  constructor(private http: HttpClient) {
  }

  findTimesheet(projectId: number, memberId: number): Observable<Timesheet> {
    return this.http.get<Timesheet>(this.baseUrl, {
      params: new HttpParams()
        .set('projectId', projectId.toString())
        .set('memberId', memberId.toString())
    });
}}


