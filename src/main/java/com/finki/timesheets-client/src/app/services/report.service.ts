import {Observable} from "rxjs";
import {ProjectTotalSalary} from "../model/ProjectTotalSalary";
import {Injectable} from "@angular/core";
import {AppConstants} from "../app.constants";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {DownloadService} from "./download.service";


@Injectable({
  providedIn: 'root'
})
export class ReportService {
  baseUrl = AppConstants.baseURL + 'reports';

  constructor(private http: HttpClient,
              private downloadService: DownloadService) {
  }

  findReport(): Observable<ProjectTotalSalary[]> {
    return this.http.get<ProjectTotalSalary[]>(`${this.baseUrl}`);
  }

  exportReportToExcel(): Observable<HttpResponse<Blob>> {

    let headers = new HttpHeaders();
    headers = headers.append('Accept', "application/vnd.ms-excel");

    return this.downloadService.downloadFile(headers, null, `${this.baseUrl}/exportExcel`);
  }
}
