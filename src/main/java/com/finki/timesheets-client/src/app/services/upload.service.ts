import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private http: HttpClient) {
  }

  public uploadFile(file, id): Observable<HttpEvent<any>> {
    let formData = new FormData();
    formData.append('file', file);
    const req = new HttpRequest('POST', `/api/items/${id}/import`, formData, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req);
  }
}
