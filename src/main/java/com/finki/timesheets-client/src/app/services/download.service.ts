import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import * as fileSaver from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class DownloadService {

  constructor(private http: HttpClient) {
  }

  downloadSelectedWordDocument(filenames: string[], projectId: number): Observable<HttpResponse<Blob>> {

    let headers = new HttpHeaders();
    let params = new HttpParams();

    headers = headers.append('Accept', "application/vnd.openxmlformats-officedocument.wordprocessingml.document; charset=utf-8");
    params = params.append('filenames', filenames.join(','));

    return this.downloadFile(headers, params, `/api/templates/project/${projectId}`);
  }

  downloadFile(headers: HttpHeaders, params: HttpParams, downloadUrl: string): Observable<HttpResponse<Blob>> {

    return this.http.get(downloadUrl, {
      headers: headers,
      params: params,
      observe: 'response',
      responseType: 'blob'
    })
  }


  getFileNameFromResponseContentDisposition(res: HttpResponse<Blob>) {
    const contentDisposition = res.headers.get('content-disposition') || '';
    const matches = /filename=([^;]+)/ig.exec(contentDisposition);
    return (matches[1] || 'untitled').trim();
  };

  saveFile(data: any, filename?: string) {
    const blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document; charset=utf-8"});
    fileSaver.saveAs(blob, filename)
  }
}
