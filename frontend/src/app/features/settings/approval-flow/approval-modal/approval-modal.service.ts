import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ApprovalModalService {
  baseUrl = environment.BASE_URL;

  constructor(private httpClient: HttpClient) {}
}
