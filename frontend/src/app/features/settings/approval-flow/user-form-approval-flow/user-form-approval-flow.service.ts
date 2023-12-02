import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { UserFormApprovalFlow } from './user-form-approval-flow';

@Injectable({
  providedIn: 'root',
})
export class UserFormApprovalFlowService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  findApprovalFlowStepsList(
    formName: string,
    user: string,
    status: string,
    page: number,
    size: number
  ): Observable<UserFormApprovalFlow[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('formName', formName)
      .set('user', user)
      .set('status', status)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<UserFormApprovalFlow[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.userFormApprovalFlow}${environment.list}`,
      { params: reqParams }
    );
  }

  deleteApprovalWorkFlowSteps(
    userId: number
  ): Observable<any | HttpErrorResponse> {
    const reqParam = new HttpParams().set('userFormApprovalFlowId', userId);
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.userFormApprovalFlow}${environment.delete}`,
      null,
      {
        params: reqParam,
        observe: 'response',
      }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.userFormApprovalFlow}${environment.count}`
    );
  }

  findAll(): Observable<UserFormApprovalFlow[] | HttpErrorResponse> {
    return this.httpClient.get<UserFormApprovalFlow[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.userFormApprovalFlow}${environment.findAll}`
    );
  }

  createUserFormApprovalFlow(
    formGroup: any
  ): Observable<HttpResponse<UserFormApprovalFlow> | HttpErrorResponse> {
    return this.httpClient.post<UserFormApprovalFlow>(
      `${this.baseUrl}${environment.userFormApprovalFlow}${environment.create}`,
      formGroup,
      { observe: 'response' }
    );
  }

  updateUserFormApprovalFlow(
    userFormApprovalFlowId: number,
    formGroup
  ): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set(
      'userFormApprovalFlowId',
      userFormApprovalFlowId
    );
    return this.httpClient.post<UserFormApprovalFlow | HttpErrorResponse>(
      `${this.baseUrl}${environment.userFormApprovalFlow}${environment.update}`,
      formGroup,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }
}
