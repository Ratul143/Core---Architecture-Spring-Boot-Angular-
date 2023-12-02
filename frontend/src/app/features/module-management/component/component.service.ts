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
import { _Component } from './component';

@Injectable({
  providedIn: 'root',
})
export class _ComponentService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  create_Component(
    _component: FormGroup
  ): Observable<HttpResponse<_Component> | HttpErrorResponse> {
    return this.httpClient.post<_Component>(
      `${this.baseUrl}${environment.component}${environment.create}`,
      _component,
      { observe: 'response' }
    );
  }

  _componentList(
    search: string,
    searchByPath: string,
    page: number,
    size: number
  ): Observable<_Component[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('search', search)
      .set('searchByPath', searchByPath)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<_Component[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.component}${environment.list}`,
      { params: reqParams }
    );
  }

  update_Component(
    uniqueKey: number,
    formGroup: _Component
  ): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set('uniqueKey', uniqueKey);
    return this.httpClient.post<_Component | HttpErrorResponse>(
      `${this.baseUrl}${environment.component}${environment.update}`,
      formGroup,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }

  delete_Component(id: number): Observable<any | HttpErrorResponse> {
    const reqParam = new HttpParams().set('uniqueKey', id);
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.component}${environment.delete}`,
      null,
      {
        params: reqParam,
        observe: 'response',
      }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.component}${environment.count}`
    );
  }

  findAll(): Observable<_Component[] | HttpErrorResponse> {
    return this.httpClient.get<_Component[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.component}${environment.findAll}`
    );
  }

  findAllByParentUniqueKeyAndKeyword(
    parentUniqueKey,
    keyword
  ): Observable<_Component[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('parentUniqueKey', parentUniqueKey)
      .set('keyword', keyword);
    return this.httpClient.get<_Component[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.component}${environment.findAllByUniqueKeyAndKeyword}`,
      { params: reqParams }
    );
  }

  findAllComponentBySubModuleUniqueKeyAndKeyword(
    parentUniqueKey,
    keyword
  ): Observable<_Component[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('parentUniqueKey', parentUniqueKey)
      .set('keyword', keyword);
    return this.httpClient.get<_Component[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.component}${environment.findAllComponentBySubModuleUniqueKeyAndKeyword}`,
      { params: reqParams }
    );
  }

  findAllComponentByModuleUniqueKeyAndKeyword(
    moduleUniqueKey,
    keyword
  ): Observable<_Component[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('moduleUniqueKey', moduleUniqueKey)
      .set('keyword', keyword);
    return this.httpClient.get<_Component[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.component}${environment.findAllOrphanComponentsByModuleUniqueKey}`,
      { params: reqParams }
    );
  }

  findAllLikeUniqueKey(
    uniqueKey: string
  ): Observable<_Component[] | HttpErrorResponse> {
    const reqParams = new HttpParams().set('uniqueKey', uniqueKey);
    return this.httpClient.get<_Component[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.component}${environment.findAllLikeUniqueKey}`,
      { params: reqParams }
    );
  }

  findComponentByPath(
    path: string
  ): Observable<_Component | HttpErrorResponse> {
    const reqParams = new HttpParams().set('path', path);
    return this.httpClient.get<_Component | HttpErrorResponse>(
      `${this.baseUrl}${environment.component}${environment.findComponentByPath}`,
      { params: reqParams }
    );
  }
}
