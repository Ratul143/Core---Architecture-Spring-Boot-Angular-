import { HttpBackend, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { ResourceService } from 'src/app/core/services/custom-services/resouce.service';

@Injectable({
  providedIn: 'root',
})
export class DatatableService extends ResourceService<any> {
  constructor(private http: HttpClient) {
    super(http);
  }
}
