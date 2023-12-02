import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { RecentVisitors } from 'src/app/core/classes/RecentVisitors';

@Injectable({
  providedIn: 'root',
})
export class RecentVisitorsService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  trackRecentVisitors(): Observable<RecentVisitors | HttpErrorResponse> {
    return this.httpClient.post<RecentVisitors>(
      `${this.baseUrl}${environment.visitor}${environment.visit}`,
      null
    );
  }

  recentVisitorsList(
    search: string,
    page: number,
    size: number
  ): Observable<RecentVisitors[]> {
    const reqParams = new HttpParams()
      .set('search', search)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<RecentVisitors[]>(
      `${this.baseUrl}${environment.visitor}${environment.list}`,
      { params: reqParams }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.visitor}${environment.count}`
    );
  }
}
