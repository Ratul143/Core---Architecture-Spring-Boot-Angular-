import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { MenuContainerObject } from './menu-container-object';

@Injectable({
  providedIn: 'root',
})
export class MainShellService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}

  findAllPermissiveModulesAndComponents(): Observable<
    MenuContainerObject | HttpErrorResponse
  > {
    return this.httpClient.get<MenuContainerObject | HttpErrorResponse>(
      `${this.baseUrl}${environment.componentPermission}${environment.findAllPermissiveModulesAndComponents}`,
      { responseType: 'json' }
    );
  }
}
