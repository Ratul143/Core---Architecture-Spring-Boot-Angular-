import { SharedModule } from './../../shared/shared-module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthRoutingModule } from './auth-routing.module';
import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [LoginComponent, AuthComponent],
  imports: [SharedModule, CommonModule, AuthRoutingModule],
})
export class AuthModule {}
