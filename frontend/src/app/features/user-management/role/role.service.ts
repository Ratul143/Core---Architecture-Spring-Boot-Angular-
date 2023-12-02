import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';
import { environment } from '../../../../environments/environment';
import { Role } from './role';

@Injectable({
  providedIn: 'root',
})
export class RoleService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  createRole(
    role: FormGroup
  ): Observable<HttpResponse<Role> | HttpErrorResponse> {
    return this.httpClient.post<Role>(
      `${this.baseUrl}${environment.role}${environment.create}`,
      role,
      { observe: 'response' }
    );
  }

  roleList(
    search: string,
    page: number,
    size: number
  ): Observable<Role[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('search', search)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<Role[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.role}${environment.list}`,
      { params: reqParams }
    );
  }

  updateRole(
    uniqueKey: number,
    formGroup: Role
  ): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set('uniqueKey', uniqueKey);
    return this.httpClient.post<Role | HttpErrorResponse>(
      `${this.baseUrl}${environment.role}${environment.update}`,
      formGroup,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }

  deleteRole(uniqueKey: string): Observable<any | HttpErrorResponse> {
    const reqParam = new HttpParams().set('uniqueKey', uniqueKey);
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.role}${environment.delete}`,
      null,
      {
        params: reqParam,
        observe: 'response',
      }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.role}${environment.count}`
    );
  }

  findAll(): Observable<Role[] | HttpErrorResponse> {
    return this.httpClient.get<Role[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.role}${environment.findAll}`
    );
  }

  findRoleById(id: number): Observable<Role[] | HttpErrorResponse> {
    const reqParams = new HttpParams().set('id', id);
    return this.httpClient.get<Role[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.role}${environment.findRoleById}`,
      { params: reqParams }
    );
  }
}
