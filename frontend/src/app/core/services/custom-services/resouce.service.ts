import { Injectable } from '@angular/core';
import {
  HttpBackend,
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export abstract class ResourceService<T> {
  private readonly BaseUrl = environment.BASE_URL;

  constructor(protected httpClient: HttpClient) {}

  toServerModel(entity: T): any {
    return entity;
  }

  fromServerModel(json: any): T {
    return json;
  }

  getListWithPage(param: any, apiUrl: string): Observable<T[]> {
    let params = new HttpParams()
      .set('limit', param.limit)
      .set('offset', param.offset)
      .set('sortColId', param.sortColId || 'created_on')
      .set('sortOrder', param.sortOrder || 'asc')
      .set('status', param.status || '')
      .set('searchText', param.searchText || '');

    return this.httpClient
      .get<T[]>(`${this.BaseUrl}/${apiUrl}?${params.toString()}`)
      .pipe(catchError(this.handleError));

    // return this.httpClient
    //   .get<ApiResponse<T>>(`${this.BaseUrl}/${apiUrl}?${params.toString()}`)
    //   .pipe(
    //     map((res) => {
    //       return res.data.map((item) => this.fromServerModel(item))
    //     }),
    //     catchError(this.handleError)
    //   );
  }

  getListByPostWithPage(
    param: any,
    resource: T,
    apiUrl: string
  ): Observable<any> {
    let params = new HttpParams()
      .set('limit', param.limit)
      .set('offset', param.offset)
      .set('sortColId', param.sortColId || 'created_on')
      .set('sortOrder', param.sortOrder || 'asc')
      .set('status', param.status || '')
      .set('searchText', param.searchText || '');

    return this.httpClient
      .post(
        `${this.BaseUrl}/${apiUrl}?${params.toString()}`,
        this.toServerModel(resource)
      )
      .pipe(catchError(this.handleError));
  }

  getList<T>(apiUrl: string): Observable<T[]> {
    return this.httpClient
      .get<T[]>(`${this.BaseUrl}${apiUrl}`)
      .pipe(retry(0), catchError(this.handleError));
  }

  get(id: string | number, apiUrl: string): Observable<T> {
    return this.httpClient.get<T>(`${this.BaseUrl}/${apiUrl}/${id}`).pipe(
      map((json) => this.fromServerModel(json)),
      catchError(this.handleError)
    );
  }

  post(resource: T, apiUrl: string): Observable<any> {
    return this.httpClient
      .post(`${this.BaseUrl}/${apiUrl}`, this.toServerModel(resource))
      .pipe(catchError(this.handleError));
  }

  imageResponsePost(resource: T, apiUrl: string): Observable<any> {
    return this.httpClient
      .post(`${this.BaseUrl}/${apiUrl}`, this.toServerModel(resource), {
        responseType: 'blob',
      })
      .pipe(catchError(this.handleError));
  }

  delete(id: string | number, apiUrl: string): Observable<any> {
    return this.httpClient
      .delete(`${this.BaseUrl}${apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  put(resource: T, apiUrl: string): Observable<any> {
    return this.httpClient
      .put(`${this.BaseUrl}/${apiUrl}`, this.toServerModel(resource))
      .pipe(catchError(this.handleError));
  }

  // Error handling
  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      if (error.status === 401) {
        errorMessage = 'Your session has expired.';
      } else {
        errorMessage = `Error Code: ${error.status}, \n Message: ${error.message}`;
      }
    }
    return throwError(() => {
      return errorMessage;
    });
  }

  getListWithPagination(resource: T, apiUrl: string): Observable<any> {
    return this.httpClient
      .post(`${this.BaseUrl}/${apiUrl}`, this.toServerModel(resource))
      .pipe(catchError(this.handleError));
  }

  getByOid(oid: string | number, apiUrl: string): Observable<any> {
    return this.httpClient
      .get(`${this.BaseUrl}/${apiUrl}/${oid}`)
      .pipe(catchError(this.handleError));
  }
}
