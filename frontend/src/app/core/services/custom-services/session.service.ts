import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AUTH_STORE_KEY, getHttpHeaders } from '../../constants/constants';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  public redirectUrl: string;
  public logoutMessage: string;
  public hasSessionExpired: boolean = false;

  constructor(private router: Router, private http: HttpClient) {}

  renewToken(): Observable<any> {
    return of(null);
    // return from(this.oAuth2Service.oAuth2.exchangeRefreshTokenForAccessToken());
  }

  revokeToken(): Observable<any> {
    return this.http.put(
      `${environment.BASE_URL}/v1/user/signout`,
      {},
      { headers: getHttpHeaders(), observe: 'response' }
    );
  }

  get isLoggedIn(): boolean {
    return !!this.getAuthInfo();
  }

  public getAuthInfo() {
    return JSON.parse(localStorage.getItem(AUTH_STORE_KEY) || '{}');
  }

  logout() {
    this.revokeToken().subscribe({
      next: (res) => {
        if (res.body.code === 200) {
          this.logoutV2();
        }
      },
      error: (err) => this.logoutV2(),
      complete: () => {},
    });
  }

  logoutV2() {
    localStorage.clear();
    this.logoutNavigate();
  }

  logoutNavigate() {
    if (this.hasSessionExpired) {
      this.router.navigate(['/session-expired']);
    } else {
      this.router.navigate(['/login']).then((isNavigated) => {
        if (isNavigated) {
        }
      });
    }
  }
}
