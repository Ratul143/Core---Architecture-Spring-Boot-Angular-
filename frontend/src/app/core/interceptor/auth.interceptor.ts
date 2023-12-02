import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) {}

  intercept(
    httpRequest: HttpRequest<any>,
    httpHandler: HttpHandler
  ): Observable<HttpEvent<any>> {
    const loginUrl = httpRequest.url.includes(`${this.auth.host}/user/login`);
    const apiColor = httpRequest.url.includes(
      `https://www.thecolorapi.com/id?hex=`
    );

    if (loginUrl || apiColor) {
      return httpHandler.handle(httpRequest);
    } else {
      this.auth.loadToken();
      const token = this.auth.getToken();
      const request = httpRequest.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
      return httpHandler.handle(request);
    }
  }
}
