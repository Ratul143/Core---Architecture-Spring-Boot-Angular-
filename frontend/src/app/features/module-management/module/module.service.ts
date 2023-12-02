import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';
import { Module } from './module';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ModuleService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  createModule(
    module: FormGroup
  ): Observable<HttpResponse<Module> | HttpErrorResponse> {
    return this.httpClient.post<Module>(
      `${this.baseUrl}${environment.module}${environment.create}`,
      module,
      { observe: 'response' }
    );
  }

  moduleList(
    search: string,
    page: number,
    size: number
  ): Observable<Module[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('search', search)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<Module[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.module}${environment.list}`,
      { params: reqParams }
    );
  }

  updateModule(
    uniqueKey: number,
    formGroup: Module
  ): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set('uniqueKey', uniqueKey);
    return this.httpClient.post<Module | HttpErrorResponse>(
      `${this.baseUrl}${environment.module}${environment.update}`,
      formGroup,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }

  deleteModule(uniqueKey: number): Observable<any | HttpErrorResponse> {
    const reqParam = new HttpParams().set('uniqueKey', uniqueKey);
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.module}${environment.delete}`,
      null,
      {
        params: reqParam,
        observe: 'response',
      }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.module}${environment.count}`
    );
  }

  findAll(): Observable<Module[] | HttpErrorResponse> {
    return this.httpClient.get<Module[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.module}${environment.findAll}`
    );
  }

  findModuleById(id: number): Observable<Module | HttpErrorResponse> {
    const reqParams = new HttpParams().set('id', id);
    return this.httpClient.get<Module | HttpErrorResponse>(
      `${this.baseUrl}${environment.module}${environment.findModuleById}`,
      { params: reqParams }
    );
  }

  findModuleComponents(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.module}${environment.findModuleComponents}`
    );
  }

  findAllModuleByKeyword(keyword): Observable<Module[] | HttpErrorResponse> {
    const reqParams = new HttpParams().set('keyword', keyword);
    return this.httpClient.get<Module[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.module}${environment.findAllByKeyword}`,
      { params: reqParams }
    );
  }

  findAllLikeUniqueKey(
    uniqueKey: string
  ): Observable<Module[] | HttpErrorResponse> {
    const reqParams = new HttpParams().set('uniqueKey', uniqueKey);
    return this.httpClient.get<Module[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.module}${environment.findAllLikeUniqueKey}`,
      { params: reqParams }
    );
  }
}
