import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';
import { environment } from '../../../../../environments/environment';
import { ApprovalWorkFlowFor } from './approval-work-flow-for';

@Injectable({
  providedIn: 'root',
})
export class ApprovalWorkFlowForService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  createApprovalWorkFlowFor(
    formGroup: FormGroup
  ): Observable<HttpResponse<ApprovalWorkFlowFor>> {
    return this.httpClient.post<ApprovalWorkFlowFor>(
      `${this.baseUrl}${environment.approvalWorkFlowFor}${environment.create}`,
      formGroup,
      { observe: 'response' }
    );
  }

  approvalWorkFlowForList(
    approvalWorkFlowFormName: string,
    approvalWorkFlowFormCode: string,
    page: number,
    size: number
  ): Observable<ApprovalWorkFlowFor[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('approvalWorkFlowFormName', approvalWorkFlowFormName)
      .set('approvalWorkFlowFormCode', approvalWorkFlowFormCode)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<ApprovalWorkFlowFor[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowFor}${environment.list}`,
      { params: reqParams }
    );
  }

  updateApprovalWorkFlowFor(
    formId: number,
    rmGroup: ApprovalWorkFlowFor
  ): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set('formId', formId);
    return this.httpClient.post<ApprovalWorkFlowFor | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowFor}${environment.update}`,
      rmGroup,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }

  deleteApprovalWorkFlowFor(
    userId: number
  ): Observable<any | HttpErrorResponse> {
    const reqParam = new HttpParams().set('userId', userId);
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowFor}${environment.delete}`,
      null,
      {
        params: reqParam,
        observe: 'response',
      }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.approvalWorkFlowFor}${environment.count}`
    );
  }

  findAll(): Observable<ApprovalWorkFlowFor[] | HttpErrorResponse> {
    return this.httpClient.get<ApprovalWorkFlowFor[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowFor}${environment.findAll}`
    );
  }
}
