import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { ApprovalWorkFlowSteps } from './approval-work-flow-steps';

@Injectable({
  providedIn: 'root',
})
export class ApprovalWorkFlowStepsService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  getApprovalFlowStepsList(
    formName: string,
    statusFrom: string,
    statusTo: string,
    page: number,
    size: number
  ): Observable<ApprovalWorkFlowSteps[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('formName', formName)
      .set('statusFrom', statusFrom)
      .set('statusTo', statusTo)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<ApprovalWorkFlowSteps[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowSteps}${environment.list}`,
      { params: reqParams }
    );
  }

  // updateApprovalWorkFlowSteps(typeId: number, ApprovalWorkFlowSteps: ApprovalWorkSteps): Observable<any | HttpErrorResponse> {
  //   const reqParams = new HttpParams().set('typeId', typeId);
  //   return this.httpClient.post<ApprovalWorkSteps | HttpErrorResponse>(`${this.baseUrl}/approval-work-flow-steps/update`, ApprovalWorkFlowSteps, {
  //     params: reqParams,
  //     observe: 'response'
  //   });
  // }

  deleteApprovalWorkFlowSteps(
    userId: number
  ): Observable<any | HttpErrorResponse> {
    const reqParam = new HttpParams().set('userId', userId);
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowSteps}${environment.delete}`,
      null,
      {
        params: reqParam,
        observe: 'response',
      }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.approvalWorkFlowSteps}${environment.count}`
    );
  }

  findAll(): Observable<ApprovalWorkFlowSteps[] | HttpErrorResponse> {
    return this.httpClient.get<ApprovalWorkFlowSteps[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowSteps}${environment.findAll}`
    );
  }

  getApprovalFlowStatusByForm(
    formId: number
  ): Observable<ApprovalWorkFlowSteps[] | HttpErrorResponse> {
    const paramReq = new HttpParams().set('formId', formId);
    return this.httpClient.get<ApprovalWorkFlowSteps[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowSteps}${environment.getByForm}`,
      { params: paramReq }
    );
  }

  createApprovalWorkFlowStepsService(
    formGroup: any
  ): Observable<HttpResponse<ApprovalWorkFlowSteps> | HttpErrorResponse> {
    return this.httpClient.post<ApprovalWorkFlowSteps>(
      `${this.baseUrl}${environment.approvalWorkFlowSteps}${environment.create}`,
      formGroup,
      { observe: 'response' }
    );
  }

  getNextStep(
    formCode: string,
    statusId: number
  ): Observable<ApprovalWorkFlowSteps | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('formCode', formCode)
      .set('statusId', statusId);
    return this.httpClient.get<ApprovalWorkFlowSteps | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowSteps}${environment.getNextStep}`,
      { params: reqParams }
    );
  }

  getPreviousStep(
    formCode: string,
    statusId: number
  ): Observable<ApprovalWorkFlowSteps | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('formCode', formCode)
      .set('statusId', statusId);
    return this.httpClient.get<ApprovalWorkFlowSteps | HttpErrorResponse>(
      `${this.baseUrl}${environment.approvalWorkFlowSteps}${environment.getPreviousStep}`,
      { params: reqParams }
    );
  }
}
