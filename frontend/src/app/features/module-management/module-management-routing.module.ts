import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../auth/login/login.component';
import { ModuleComponent } from './module/module.component';
import { SubModuleComponent } from './sub-module/sub-module.component';
import { _ComponentComponent } from './component/component.component';
import { UserBasedPermissionComponent } from './user-based-permission/user-based-permission.component';
import { RoleBasedPermissionComponent } from './role-based-permission/role-based-permission.component';
import { ModuleManagementComponent } from './module-management.component';

const routes: Routes = [
  {
    path: '',
    component: ModuleManagementComponent,
    children: [
      {
        path: 'module',
        component: ModuleComponent,
      },
      {
        path: 'sub-module',
        component: SubModuleComponent,
      },
      {
        path: 'component',
        component: _ComponentComponent,
      },
      {
        path: 'user-based-permission',
        component: UserBasedPermissionComponent,
      },
      {
        path: 'role-based-permission',
        component: RoleBasedPermissionComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModuleManagementRoutingModule {}
