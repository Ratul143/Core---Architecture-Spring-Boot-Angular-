import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class JasperService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  // getReport() {
  //   return this.httpClient.get(this.baseUrl + '/jasper-report/report', {observe: 'response', responseType: 'blob'});
  // }

  findReportById(id, path) {
    const params = new HttpParams().set('id', id);
    return this.httpClient.get(this.baseUrl + path, {
      observe: 'response',
      responseType: 'blob',
      params: params,
    });
  }

  getReportByRmCode(rmCode, path) {
    const params = new HttpParams().set('rmCode', rmCode);
    return this.httpClient.get(this.baseUrl + path, {
      observe: 'response',
      responseType: 'blob',
      params: params,
    });
  }
}
