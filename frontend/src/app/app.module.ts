import { LayoutModule } from './layout/layout.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared-module';
import en from '@angular/common/locales/en';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import {
  HashLocationStrategy,
  LocationStrategy,
  registerLocaleData,
} from '@angular/common';
import { AppRoutingModule } from './app-routing.module';
import { AuthInterceptor } from './core/interceptor/auth.interceptor';
import { InterceptorService } from './core/interceptor/interceptor.service';
import { NotificationModule } from './core/modules/notification.module';

registerLocaleData(en);

@NgModule({
  declarations: [AppComponent],
  imports: [
    SharedModule,
    BrowserModule,
    HttpClientModule,
    NotificationModule,
    LayoutModule,
    RouterModule,
    BrowserAnimationsModule,
    AppRoutingModule,
  ],
  providers: [
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: InterceptorService, multi: true },
    { provide: NZ_I18N, useValue: en_US },
  ],
  exports: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
