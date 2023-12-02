import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from '../../../environments/environment';
import { RolesEnum } from '../classes/rolesEnum';
import { FormGroup } from '@angular/forms';
import { AuthorityEnum } from '../classes/authority-enum';
import { User } from 'src/app/features/user-management/user/user';
import { UserBasedPermissionService } from 'src/app/features/module-management/user-based-permission/user-based-permission.service';
import { RoleBasedPermissionService } from 'src/app/features/module-management/role-based-permission/role-based-permission.service';
import { AccsAuthUser } from 'src/app/models/accs-auth-user/accs-auth-user';
import {
  AUTH_STORE_KEY,
  CREATE_ACTION,
  DELETE_ACTION,
  READ_ACTION,
  SIDENAV_ELEMENTS_KEY,
  UPDATE_ACTION,
  USER_INFO,
} from '../constants/constants';

@Injectable({
  providedIn: 'root',
})

/*--
  [Note]: {observe: 'response'} returns the whole response including header.
  By default, http response returns the body only.
 --*/
export class AuthService {
  public host = environment.BASE_URL;
  private token: string;
  private loggedInUsername: string;
  private jwtHelper = new JwtHelperService();
  private authorities: string[] = [];

  constructor(
    private http: HttpClient,
    private componentPermissionService: UserBasedPermissionService,
    private moduleForRoleService: RoleBasedPermissionService
  ) {}

  public get isDeveloper(): boolean {
    return this.findUserRole() === RolesEnum.ROLE_DEVELOPER;
  }

  public get isSuperAdmin(): boolean {
    return (
      this.findUserRole() === RolesEnum.ROLE_SUPER_ADMIN || this.isDeveloper
    );
  }

  public get isRmInventory(): boolean {
    return (
      this.findUserRole() === RolesEnum.ROLE_RM_INVENTORY ||
      this.isDeveloper ||
      this.isSuperAdmin ||
      this.isInventoryAdmin
    );
  }

  public get isDesigner(): boolean {
    return (
      this.findUserRole() === RolesEnum.ROLE_DESIGNER ||
      this.isDeveloper ||
      this.isSuperAdmin
    );
  }

  public get isMerchandiser(): boolean {
    return (
      this.findUserRole() === RolesEnum.ROLE_MERCHANDISER ||
      this.isDeveloper ||
      this.isSuperAdmin
    );
  }

  public get isInventoryFg(): boolean {
    return (
      this.findUserRole() === RolesEnum.ROLE_INVENTORY_FG ||
      this.isDeveloper ||
      this.isSuperAdmin
    );
  }

  public get isMarketing(): boolean {
    return (
      this.findUserRole() === RolesEnum.ROLE_MARKETING ||
      this.isDeveloper ||
      this.isSuperAdmin
    );
  }

  public get isInventoryAdmin(): boolean {
    return (
      this.findUserRole() === RolesEnum.ROLE_INVENTORY_ADMIN ||
      this.isDeveloper ||
      this.isSuperAdmin
    );
  }

  login(user: FormGroup): Observable<HttpResponse<User>> {
    return this.http.post<User>(
      `${this.host}${environment.user}${environment.login}`,
      user,
      {
        observe: 'response',
      }
    );
  }

  register(user: FormGroup): Observable<HttpResponse<any>> {
    return this.http.post<any>(
      `${this.host}${environment.user}${environment.register}`,
      user
    );
  }

  logOut(): void {
    this.token = null;
    this.loggedInUsername = null;
    localStorage.removeItem(USER_INFO);
    localStorage.removeItem(AUTH_STORE_KEY);
    localStorage.removeItem(SIDENAV_ELEMENTS_KEY);
  }

  saveTokenToTheLocalStorage(token: string): void {
    this.token = token;
    localStorage.setItem(AUTH_STORE_KEY, token);
  }

  saveUserToTheLocalStorage(user: any): void {
    localStorage.setItem(USER_INFO, JSON.stringify(user));
  }

  loadToken(): void {
    this.token = localStorage.getItem(AUTH_STORE_KEY);
  }

  getToken(): string {
    return this.token;
  }

  isUserLoggedIn(): boolean {
    this.loadToken();
    if (
      this.token != null &&
      this.token !== '' &&
      (this.jwtHelper.decodeToken(this.token).sub != null || '') &&
      !this.jwtHelper.isTokenExpired(this.token)
    ) {
      this.loggedInUsername = this.jwtHelper.decodeToken(this.token).sub;
      return true;
    } else {
      this.logOut();
      return false;
    }
  }

  findUserFromLocalCache(): AccsAuthUser {
    return JSON.parse(localStorage.getItem(USER_INFO));
  }

  findUserRole(): string {
    return this.findUserFromLocalCache()?.role?.role;
  }

  permission(component, action): boolean {
    const permissions = JSON.parse(
      atob(localStorage.getItem(SIDENAV_ELEMENTS_KEY))
    );
    if (permissions) {
      for (const element of permissions) {
        const componentPath = element?.component?.path.replace(
          /^\/[^/]+\//,
          ''
        );

        if (componentPath === component) {
          if (action === CREATE_ACTION) return element.create === true;
          if (action === READ_ACTION) return element.read === true;
          if (action === UPDATE_ACTION) return element.update === true;
          if (action === DELETE_ACTION) return element.delete === true;
        }
      }
    }
    return false;
  }
}
