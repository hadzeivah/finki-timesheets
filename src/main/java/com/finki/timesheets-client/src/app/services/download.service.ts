import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {AppConstants} from "../app.constants";
import {Observable} from "rxjs";
import * as fileSaver from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class DownloadService {

  baseUrl = AppConstants.baseURL + 'templates/';

  constructor(private http: HttpClient) { }

  downloadClasspathFile(filename: string): Observable<HttpResponse<Blob>>{

    let headers = new HttpHeaders();
    headers = headers.append('Accept',"application/vnd.openxmlformats-officedocument.wordprocessingml.documentrtg; charset=utf-8");

    return this.http.get(this.baseUrl + filename ,{
      headers: headers ,
      observe: 'response',
      responseType: 'blob'
    })
  }

  getFileNameFromResponseContentDisposition (res: HttpResponse<Blob>) {
    const contentDisposition = res.headers.get('content-disposition') || '';
    const matches = /filename=([^;]+)/ig.exec(contentDisposition);
    return (matches[1] || 'untitled').trim();
  };

  saveFile(data: any, filename?: string){
    const blob = new Blob([data] , {type: "application/vnd.openxmlformats-officedocument.wordprocessingml.documentrtg; charset=utf-8"})
    fileSaver.saveAs(blob, filename)
  }

}
