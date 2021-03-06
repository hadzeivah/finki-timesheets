import {Observable} from "rxjs";
import {ProjectTotalSalary} from "../model/ProjectTotalSalary";
import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {DownloadService} from "./download.service";
import {ReportByIO} from "../model/ReportByIO";


@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient,
              private downloadService: DownloadService) {
  }

  findReport(): Observable<ProjectTotalSalary[]> {
    return this.http.get<ProjectTotalSalary[]>('/api/reports');
  }

  findReportTotalByIo() {
    return this.http.get<ReportByIO[]>('/api/reports/total_by_io');
  }

  exportReportToExcel(): Observable<HttpResponse<Blob>> {

    let headers = new HttpHeaders();
    headers = headers.append('Accept', "application/vnd.ms-excel");

    return this.downloadService.downloadFile(headers, null, `/api/reports/exportExcel`);
  }
}
