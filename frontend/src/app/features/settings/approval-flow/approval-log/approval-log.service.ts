import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { ApprovalLog } from './approval-log';

@Injectable({
  providedIn: 'root',
})
export class ApprovalLogService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  findLogsByUniqueId(
    formUniqueId: string
  ): Observable<ApprovalLog[] | HttpErrorResponse> {
    const reqParams = new HttpParams().set('formUniqueId', formUniqueId);
    return this.httpClient.get<ApprovalLog[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.logs}${environment.findByFormUniqueId}`,
      { params: reqParams }
    );
  }
}
