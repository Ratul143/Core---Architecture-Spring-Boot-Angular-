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
import { RoleBasedPermission } from './role-based-permission';
import { MenuContainerObject } from 'src/app/layout/main-shell/menu-container-object';

@Injectable({
  providedIn: 'root',
})
export class RoleBasedPermissionService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  createOrUpdateRoleBasedPermission(
    roleUniqueKey: string,
    moduleForRole: FormGroup
  ): Observable<HttpResponse<RoleBasedPermission> | HttpErrorResponse> {
    const reqParams = new HttpParams().set('roleUniqueKey', roleUniqueKey);
    return this.httpClient.post<RoleBasedPermission>(
      `${this.baseUrl}${environment.roleBasedPermission}${environment.createOrUpdate}`,
      moduleForRole,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }

  findAll(): Observable<RoleBasedPermission[] | HttpErrorResponse> {
    return this.httpClient.get<RoleBasedPermission[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.roleBasedPermission}${environment.findAll}`
    );
  }

  findAllSidenavElements(): Observable<
    MenuContainerObject | HttpErrorResponse
  > {
    return this.httpClient.get<MenuContainerObject | HttpErrorResponse>(
      `${this.baseUrl}${environment.roleBasedPermission}${environment.findAllSidenavElements}`,
      { responseType: 'json' }
    );
  }

  findAllByRoleUniqueKey(
    roleUniqueKey: string,
    searchByModule: string,
    searchBySubModule: string,
    searchByComponent: string,
    searchByAny: string,
    page: number,
    size: number
  ): Observable<MenuContainerObject | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('roleUniqueKey', roleUniqueKey)
      .set('searchByModule', searchByModule)
      .set('searchBySubModule', searchBySubModule)
      .set('searchByComponent', searchByComponent)
      .set('searchByAny', searchByAny)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<MenuContainerObject | HttpErrorResponse>(
      `${this.baseUrl}${environment.roleBasedPermission}${environment.findAllByRoleId}`,
      {
        responseType: 'json',
        params: reqParams,
      }
    );
  }

  findComponentPermission(
    componentId: number
  ): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set('componentId', componentId);
    return this.httpClient.get<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.roleBasedPermission}${environment.findComponentPermission}`,
      { params: reqParams }
    );
  }
}
