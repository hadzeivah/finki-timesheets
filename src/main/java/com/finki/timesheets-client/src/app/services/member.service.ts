import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiResponse} from "../model/api.response";
import {Member} from "../model/Member";
import {PositionType} from "../model/PositionType";
import {MemberProjectsDto} from "../model/MemberProjectsDto";

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  constructor(private http: HttpClient) {
  }

  findMembers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`/api/members`);
  }

  addMember(member: Member): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`/api/members`, member);
  }

  deleteMember(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`/api/members/${id}`);
  }

  updateMember(member: Member): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`/api/members/${member.id}`, member);
  }

  getMemberTypes(): Observable<PositionType[]> {
    return this.http.get<PositionType[]>('/api/members/positions')
  }

  findMemberById(id: number): Observable<Member> {
    return this.http.get<Member>(`/api/members/${id}`);
  }

  findMembersByProject(id: number): Observable<Member[]> {
    return this.http.get<Member[]>(`/api/members/project/${id}`);
  }

  findMembersDetails(): Observable<MemberProjectsDto[]> {
    return this.http.get<MemberProjectsDto[]>(`/api/members/details`);
  }

}
