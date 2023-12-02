import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ModuleManagementRoutingModule} from './module-management-routing.module';
import {ModuleComponent, ModuleCreateEditComponent} from "./module/module.component";
import {
  UserBasedCreateEditPermission,
  UserBasedPermissionComponent,
} from "./user-based-permission/user-based-permission.component";
import {SubModuleComponent, SubModuleCreateEditComponent} from "./sub-module/sub-module.component";
import {RoleComponent, RoleCreateEditComponent} from "../user-management/role/role.component";
import {_ComponentComponent, _ComponentCreateEditComponent} from "./component/component.component";
import {
  ModuleForRoleCreateEditComponent,
  RoleBasedPermissionComponent
} from "./role-based-permission/role-based-permission.component";
import {SharedModule} from "../../shared/shared-module";
import {ModuleManagementComponent} from "./module-management.component";


@NgModule({
  declarations: [
    ModuleManagementComponent,
    RoleComponent,
    RoleCreateEditComponent,
    ModuleComponent,
    ModuleCreateEditComponent,
    SubModuleComponent,
    SubModuleCreateEditComponent,
    RoleBasedPermissionComponent,
    ModuleForRoleCreateEditComponent,
    _ComponentComponent,
    _ComponentCreateEditComponent,
    UserBasedPermissionComponent,
    UserBasedCreateEditPermission,],
  imports: [
    SharedModule,
    CommonModule,
    ModuleManagementRoutingModule,

  ]
})
export class ModuleManagementModule {
}
