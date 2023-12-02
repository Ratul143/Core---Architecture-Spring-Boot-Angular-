import { MainShellComponent } from './layout/main-shell/main-shell.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomPreloadingStrategy } from './core/services/custom-preloading-strategy';
import { AuthGuard } from './core/auth/auth.guard';
import { LoginComponent } from './features/auth/login/login.component';

const routes: Routes = [
  {
    path: '',
    component: MainShellComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', component: LoginComponent, pathMatch: 'full' },
      {
        path: 'dashboard',
        loadChildren: () =>
          import('./features/dashboard/dashboard.module').then(
            (m) => m.DashboardModule
          ),
      },
      {
        path: 'settings',
        loadChildren: () =>
          import('./features/settings/settings.module').then(
            (m) => m.SettingsModule
          ),
      },
      {
        path: 'module-management',
        loadChildren: () =>
          import('./features/module-management/module-management.module').then(
            (m) => m.ModuleManagementModule
          ),
      },
      {
        path: 'user-management',
        loadChildren: () =>
          import('./features/user-management/user-management.module').then(
            (m) => m.UserManagementModule
          ),
      },
    ],
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: '**',
    redirectTo: 'not-found',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
