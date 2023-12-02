import { Injectable, Injector } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';

import { SessionService } from './session.service';
import { tap, catchError, switchMap } from 'rxjs/operators';
import { AUTH_STORE_KEY } from '../../constants/constants';

@Injectable()
export class TokenInterceptorService implements HttpInterceptor {
  constructor(private injector: Injector) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const sessionService = this.injector.get(SessionService);

    // console.log(request); //--------------------------------

    let accessToken = null;
    let authInfo = JSON.parse(localStorage.getItem(AUTH_STORE_KEY)!);
    if (localStorage.hasOwnProperty(AUTH_STORE_KEY) && authInfo.access_token) {
      accessToken = authInfo.access_token;
    }

    if (this.isInvalidToken(accessToken)) {
      this.logoutV2(sessionService, 'You are not logged in');
      throw new Error(`Access token not found`);
    }
    return next.handle(this.customRequest(request, accessToken)).pipe(
      tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          return event;
        }
        return '';
      }),
      catchError((error: any) => {
        if (error instanceof HttpErrorResponse) {
          if (error.status === 401) {
            this.logoutV2(
              sessionService,
              'Your session has expired. Please login again',
              true
            );
            // const refreshToken = authInfo.refresh_token;
            // if (this.isInvalidToken(refreshToken)) {
            //     this.logoutV2(sessionService, 'You are not logged in');
            //     return throwError(() => { return 'Refresh token not found'; });
            // }
            // return sessionService.renewToken().pipe(
            //     switchMap((_ => {
            //         authInfo = JSON.parse(localStorage.getItem(AUTH_STORE_KEY)!);
            //         const access_token = authInfo.access_token;
            //         if (this.isInvalidToken(access_token)) {
            //             this.logoutV2(sessionService, 'You are not logged in');
            //             return throwError(() => { return 'Access token not found'; });
            //         }
            //         return next.handle(this.customRequest(request, access_token)).pipe(tap((evn: HttpEvent<any>) => {
            //             if (evn instanceof HttpResponse) {
            //                 return evn;
            //             }
            //             return '';
            //         }),catchError((err: any) => {
            //             err.loggedIn = true;
            //             return throwError(() => { return err });
            //         }));
            //     })),catchError((err: any) => {
            //         if (err.loggedIn !== true) {
            //             this.logoutV2(sessionService, 'Your session has expired. Please login again', true);
            //         }
            //         return throwError(() => { return err });
            //     })
            // )
          }
        }
        return throwError(() => {
          return error;
        });
      })
    );
  }

  isInvalidToken(token: string) {
    return (
      typeof token === 'undefined' ||
      token === null ||
      token.toString().length === 0
    );
  }

  customRequest(request: HttpRequest<any>, accessToken: string) {
    const req = request.clone({
      setHeaders: {
        Authorization: 'Bearer {0}'.replace('{0}', accessToken),
      },
    });
    return req;
  }

  logoutV2(
    sessionService: SessionService,
    logoutMessage: string,
    hasSessionExpired: boolean = false
  ) {
    const router = this.injector.get(Router);
    sessionService.redirectUrl = router.url;
    sessionService.logoutMessage = logoutMessage;
    sessionService.hasSessionExpired = hasSessionExpired;
    sessionService.logoutV2();
  }
}
