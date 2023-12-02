import { HttpBackend, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { getHttpHeaders } from 'src/app/core/constants/constants';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  public host = environment.BASE_URL;

  constructor(private http: HttpClient, private backend: HttpBackend) {}

  login(credentials: any): Observable<any> {
    let http = new HttpClient(this.backend);
    return http.post(
      `${this.host}${environment.user}${environment.signIn}`,
      credentials,
      {
        headers: getHttpHeaders(),
        observe: 'response',
      }
    );
  }
}
