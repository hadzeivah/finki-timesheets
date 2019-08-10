import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppConstants} from "../app.constants";
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";
import {Member} from "../model/Member";

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  baseUrl = AppConstants.baseURL + 'members';

  constructor(private http: HttpClient) { }

  findMembers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }
  addMember(member: Member): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, member);
  }
  deleteMember(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + '/' + id);
  }
  updateMember(member: Member): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl +  '/' + member.id, member);
  }
}
