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
import { SubModule } from './sub-module';

@Injectable({
  providedIn: 'root',
})
export class SubModuleService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  createSubModule(
    subModule: FormGroup
  ): Observable<HttpResponse<SubModule> | HttpErrorResponse> {
    return this.httpClient.post<SubModule>(
      `${this.baseUrl}${environment.subModule}${environment.create}`,
      subModule,
      { observe: 'response' }
    );
  }

  subModuleList(
    search: string,
    page: number,
    size: number
  ): Observable<SubModule[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('search', search)
      .set('page', page)
      .set('size', size);
    return this.httpClient.get<SubModule[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.subModule}${environment.list}`,
      { params: reqParams }
    );
  }

  updateSubModule(
    uniqueKey: number,
    formGroup: SubModule
  ): Observable<any | HttpErrorResponse> {
    const reqParams = new HttpParams().set('uniqueKey', uniqueKey);
    return this.httpClient.post<SubModule | HttpErrorResponse>(
      `${this.baseUrl}${environment.subModule}${environment.update}`,
      formGroup,
      {
        params: reqParams,
        observe: 'response',
      }
    );
  }

  deleteSubModule(id: number): Observable<any | HttpErrorResponse> {
    const reqParam = new HttpParams().set('id', id);
    return this.httpClient.post<any | HttpErrorResponse>(
      `${this.baseUrl}${environment.subModule}${environment.delete}`,
      null,
      {
        params: reqParam,
        observe: 'response',
      }
    );
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get<any>(
      `${this.baseUrl}${environment.subModule}${environment.count}`
    );
  }

  findAll(): Observable<SubModule[] | HttpErrorResponse> {
    return this.httpClient.get<SubModule[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.subModule}${environment.findAll}`
    );
  }

  findSubModuleById(id: number): Observable<SubModule | HttpErrorResponse> {
    const reqParams = new HttpParams().set('id', id);
    return this.httpClient.get<SubModule | HttpErrorResponse>(
      `${this.baseUrl}${environment.subModule}${environment.findSubmoduleById}`,
      { params: reqParams }
    );
  }

  findAllSubModulesByModuleId(
    moduleId: number
  ): Observable<SubModule[] | HttpErrorResponse> {
    const reqParams = new HttpParams().set('moduleId', moduleId);
    return this.httpClient.get<SubModule[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.subModule}${environment.findAllSubmodulesByModuleId}`,
      { params: reqParams }
    );
  }

  findAllByParentUniqueKeyAndKeyword(
    parentUniqueKey,
    keyword
  ): Observable<SubModule[] | HttpErrorResponse> {
    const reqParams = new HttpParams()
      .set('parentUniqueKey', parentUniqueKey)
      .set('keyword', keyword);
    return this.httpClient.get<SubModule[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.subModule}${environment.findAllByUniqueKeyAndKeyword}`,
      { params: reqParams }
    );
  }

  findAllLikeUniqueKey(
    uniqueKey: string
  ): Observable<SubModule[] | HttpErrorResponse> {
    const reqParams = new HttpParams().set('uniqueKey', uniqueKey);
    return this.httpClient.get<SubModule[] | HttpErrorResponse>(
      `${this.baseUrl}${environment.subModule}${environment.findAllLikeUniqueKey}`,
      { params: reqParams }
    );
  }
}
