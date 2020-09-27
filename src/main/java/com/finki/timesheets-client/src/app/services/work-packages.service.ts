import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {WorkPackage} from "../model/WorkPackage";
import {Task} from '../model/Task';
import {Output} from "../model/Output";

@Injectable({
  providedIn: 'root'
})
export class WorkPackagesService {

  constructor(private http: HttpClient) {
  }

  findWorkPackages(): Observable<WorkPackage[]> {
    return this.http.get<WorkPackage[]>(`/api/work_packages`);
  }

  saveTask(workPackageId: number, task: Task): Observable<Task> {
    return this.http.post<Task>(`/api/work_packages/${workPackageId}/task`, task);
  }

  saveOutput(workPackageId: number, output: Output): Observable<Output> {
    return this.http.post<Output>(`/api/work_packages/${workPackageId}/output`, output);
  }

  saveWorkPackage(workPackage: WorkPackage): Observable<WorkPackage> {
    return this.http.post<WorkPackage>(`/api/work_packages`, workPackage);
  }
}
