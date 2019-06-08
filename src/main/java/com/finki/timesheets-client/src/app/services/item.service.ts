import {Injectable} from '@angular/core';
import {AppConstants} from '../app.constants';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Item} from '../model/Item';
import {ApiResponse} from '../model/api.response';

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  baseUrl = AppConstants.baseURL + 'items';

  constructor(private http: HttpClient) {
  }

  findItems(timesheetId: number): Observable<Item[]> {
    return this.http.get<Item[]>(this.baseUrl, {
      params: new HttpParams()
        .set('timesheetId', timesheetId.toString())
    });
  }
  addItem(item: Item): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, item);
  }
  deleteItem(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + '/' + id);
  }
  updateItem(item: Item): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl +  '/' + item.id, item);
  }
}
