import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UserManagementRoutingModule} from './user-management-routing.module';
import {UserManagementComponent} from "./user-management.component";
import {SharedModule} from "../../shared/shared-module";
import {CreateEditNewUserDialog, UserComponent} from "./user/user.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";


@NgModule({
  declarations: [
    UserManagementComponent,
    UserComponent,
    CreateEditNewUserDialog,
    ChangePasswordComponent,
  ],
  imports: [
    SharedModule,
    CommonModule,
    UserManagementRoutingModule
  ]
})
export class UserManagementModule {
}
