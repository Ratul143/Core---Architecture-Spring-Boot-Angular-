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
import { UserBasedPermission } from './user-based-permission';
import { MenuContainerObject } from 'src/app/layout/main-shell/menu-container-object';

@Injectable({
  providedIn: 'root',
})
export class UserBasedPermissionService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  createOrUpdateUserBasedPermission(
    userId: number,
    roleId: number,
    moduleForRole: FormGroup
  ): Observable<HttpResponse<UserBasedPermission> | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('userId', userId)
      .set('roleId', roleId);
    return this.httpClient.post<UserBasedPermission>(
      `${this.baseUrl}${environment.userBasedPermission}${environment.createOrUpdate}`,
      moduleForRole,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }

  findAllByRoleAndUserId(
    userId: number,
    roleId: number
  ): Observable<MenuContainerObject | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('userId', userId)
      .set('roleId', roleId);
    return this.httpClient.get<MenuContainerObject | HttpErrorResponse>(
      `${this.baseUrl}${environment.userBasedPermission}${environment.findAllByUserAndRoleId}`,
      {
        responseType: 'json',
        params: reqParams,
      }
    );
  }

  revertToDefaultRole(userId: number): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set('userId', userId);
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.userBasedPermission}${environment.revertToDefaultRole}`,
      null,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }
}
