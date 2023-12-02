import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../auth/login/login.component';
import { UserComponent } from './user/user.component';
import { UserManagementComponent } from './user-management.component';
import { RoleComponent } from './role/role.component';

const routes: Routes = [
  {
    path: '',
    component: UserManagementComponent,
    children: [
      { path: 'user', component: UserComponent },
      { path: 'role', component: RoleComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserManagementRoutingModule {}
