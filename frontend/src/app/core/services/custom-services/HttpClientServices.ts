import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { forkJoin, Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class HttpclientService {
  constructor(private _httpClient: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  // HttpClient API get() method => Fetch details
  get<T>(apiUrl: string) {
    return this._httpClient
      .get<T>(`${environment.BASE_URL}/${apiUrl}`)
      .pipe(retry(1), catchError(this.handleError));
  }
  // HttpClient API get() method => Fetch details
  getList<T>(apiUrl: string) {
    return this._httpClient
      .get<T[]>(`${environment.BASE_URL}/${apiUrl}`)
      .pipe(retry(1), catchError(this.handleError));
  }
  // HttpClient API post() method => Create new record
  post(paylods: any, apiUrl: string) {
    return this._httpClient
      .post(environment.BASE_URL, paylods, this.httpOptions)
      .pipe(retry(1), catchError(this.handleError));
  }
  // HttpClient API put() method => update record
  put(paylods: any, apiUrl: string) {
    return this._httpClient
      .put(`${environment.BASE_URL}/${apiUrl}`, paylods, this.httpOptions)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Error handling
  private handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => {
      return errorMessage;
    });
  }
}
