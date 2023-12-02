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
import { ApprovalWorkFlow } from './approval-work-flow';
import { ApprovalWorkFlowStatus } from '../approval-work-flow-status/approval-work-flow-status';

@Injectable({
  providedIn: 'root',
})
export class ApprovalWorkFlowService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  createApprovalWorkFlow(
    formGroup: FormGroup
  ): Observable<HttpResponse<ApprovalWorkFlow>> {
    return this.httpClient.post<ApprovalWorkFlow>(
      `${this.baseUrl}${environment.approvalWorkFlow}${environment.create}`,
      formGroup,
      { observe: 'response' }
    );
  }

  approvalWorkFlowForList(
    searchValueForForm: string,
    searchValueForStatus: string,
    searchValueForCode: string,
    page: number,
    size: number
  ): Observable<ApprovalWorkFlow[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('searchValueForForm', searchValueForForm)
      .set('searchValueForStatus', searchValueForStatus)
      .set('searchValueForCode', searchValueForCode)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<ApprovalWorkFlow[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlow}${environment.list}`,
      { params: reqParams }
    );
  }

  updateApprovalWorkFlow(
    approvalWorkFlowId: number,
    rmGroup: ApprovalWorkFlow
  ): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set(
      'approvalWorkFlowId',
      approvalWorkFlowId
    );
    return this.httpClient.post<ApprovalWorkFlow | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlow}${environment.update}`,
      rmGroup,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }

  deleteApprovalWorkFlow(
    approvalWorkFlowId: number
  ): Observable<any | HttpErrorResponse> {
    const reqParam = new HttpParams().set(
      'approvalWorkFlowId',
      approvalWorkFlowId
    );
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlow}${environment.delete}`,
      null,
      {
        params: reqParam,
        observe: 'response',
      }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.approvalWorkFlow}${environment.count}`
    );
  }

  findAll(): Observable<ApprovalWorkFlow[] | HttpErrorResponse> {
    return this.httpClient.get<ApprovalWorkFlow[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlow}${environment.findAll}`
    );
  }

  findApprovalFlowStatusByForm(
    formId: number
  ): Observable<ApprovalWorkFlowStatus[] | HttpErrorResponse> {
    const paramReq = new HttpParams().set('formId', formId);
    return this.httpClient.get<ApprovalWorkFlowStatus[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlow}${environment.getByForm}`,
      { params: paramReq }
    );
  }
}
