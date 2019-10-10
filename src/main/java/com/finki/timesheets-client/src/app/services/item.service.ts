import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Item} from '../model/Item';
import {ApiResponse} from '../model/api.response';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient) {
  }

  findItems(timesheetId: number): Observable<Item[]> {
    return this.http.get<Item[]>(`/api/items`, {
      params: new HttpParams()
        .set('timesheetId', timesheetId.toString())
    });
  }
  addItem(item: Item): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`/api/items`, item);
  }
  deleteItem(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`/api/items/${id}`);
  }
  updateItem(item: Item): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`/api/items/${item.id}`, item);
  }
}
