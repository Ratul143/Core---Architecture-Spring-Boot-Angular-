import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { ApprovalWorkFlowStatus } from './approval-work-flow-status';

@Injectable({
  providedIn: 'root',
})
export class ApprovalWorkFlowStatusService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  findApprovalFlowStatusList(
    statusName: string,
    statusCode: string,
    page: number,
    size: number
  ): Observable<ApprovalWorkFlowStatus[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('statusName', statusName)
      .set('statusCode', statusCode)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<ApprovalWorkFlowStatus[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowStatus}${environment.list}`,
      { params: reqParams }
    );
  }

  deleteApprovalWorkFlowStatus(
    statusId: number
  ): Observable<any | HttpErrorResponse> {
    const reqParam = new HttpParams().set('statusId', statusId);
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowStatus}${environment.delete}`,
      null,
      {
        params: reqParam,
        observe: 'response',
      }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.approvalWorkFlowStatus}${environment.count}`
    );
  }

  findAll(): Observable<ApprovalWorkFlowStatus[] | HttpErrorResponse> {
    return this.httpClient.get<ApprovalWorkFlowStatus[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowStatus}${environment.findAll}`
    );
  }

  findApprovalFlowStatusByForm(
    formId: number
  ): Observable<ApprovalWorkFlowStatus[] | HttpErrorResponse> {
    const paramReq = new HttpParams().set('formId', formId);
    return this.httpClient.get<ApprovalWorkFlowStatus[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowStatus}${environment.getByForm}`,
      { params: paramReq }
    );
  }

  createApprovalWorkFlowStatus(
    formGroup: any
  ): Observable<HttpResponse<ApprovalWorkFlowStatus> | HttpErrorResponse> {
    return this.httpClient.post<ApprovalWorkFlowStatus>(
      `${this.baseUrl}${environment.approvalWorkFlowStatus}${environment.create}`,
      formGroup,
      { observe: 'response' }
    );
  }

  updateApprovalWorkFlowStatus(
    statusId: number,
    ApprovalWorkFlowStatus: ApprovalWorkFlowStatus
  ): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set('statusId', statusId);
    return this.httpClient.post<ApprovalWorkFlowStatus | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowStatus}${environment.update}`,
      ApprovalWorkFlowStatus,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }
}
