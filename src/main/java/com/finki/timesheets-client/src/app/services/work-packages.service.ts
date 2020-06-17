import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {WorkPackage} from "../model/WorkPackage";

@Injectable({
  providedIn: 'root'
})
export class WorkPackagesService {

  constructor(private http: HttpClient) {
  }

  findWorkPackages(): Observable<WorkPackage[]> {
    return this.http.get<WorkPackage[]>(`/api/work_packages`);
  }

  saveTasks(workPackage: WorkPackage): Observable<WorkPackage> {
    return this.http.post<WorkPackage>(`/api/work_packages/tasks`, workPackage);
  }

  saveOutputs(workPackage: WorkPackage): Observable<WorkPackage> {
    return this.http.post<WorkPackage>(`/api/work_packages/outputs`, workPackage);
  }

  saveWorkPackage(workPackage: WorkPackage): Observable<WorkPackage> {
    return this.http.post<WorkPackage>(`/api/work_packages`, workPackage);
  }
}
