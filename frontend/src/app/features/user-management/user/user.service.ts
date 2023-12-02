import { Injectable } from '@angular/core';
import {
  HttpBackend,
  HttpClient,
  HttpErrorResponse,
  HttpEvent,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { User } from './user';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';
import { HREmployees } from './hr-employees';
import { environment } from '../../../../environments/environment';
import { AccsUserList } from 'src/app/models/accs-auth-user/accs-user-list';
import { ResourceService } from 'src/app/core/services/custom-services/resouce.service';
import { CustomResponse } from 'src/app/models/response/custom-response';
import { AuthUser } from 'src/app/models/accs-auth-user/auth-user';
import { EmployeeList } from 'src/app/models/accs-auth-user/employee-list';
import { EmployeeType } from 'src/app/models/accs-auth-user/employee-type';

@Injectable({
  providedIn: 'root',
})
export class UserService extends ResourceService<any> {
  private host = environment.BASE_URL;

  constructor(private http: HttpClient, private backend: HttpBackend) {
    super(http);
  }

  userList(
    searchValueForUsername: string,
    searchValueForEmail: string,
    searchValueForUserType: string,
    searchValueForCellNo: string,
    page: number,
    size: number
  ): Observable<AccsUserList[]> {
    const reqParams = new HttpParams()
      .set('searchValueForUsername', searchValueForUsername)
      .set('searchValueForEmail', searchValueForEmail)
      .set('searchValueForUserType', searchValueForUserType)
      .set('searchValueForCellNo', searchValueForCellNo)
      .set('page', page)
      .set('size', size);
    return this.http.get<AccsUserList[]>(
      `${this.host}${environment.user}${environment.findAll}${environment.authUsers}`,
      {
        params: reqParams,
      }
    );
  }

  addUser(
    formData: FormData
  ): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<HttpResponse<AuthUser>>(
      `${this.host}${environment.user}${environment.add}`,
      formData
    );
  }

  updateUser(formData: FormData): Observable<any> {
    return this.http.post<any>(
      `${this.host}${environment.user}${environment.update}`,
      formData
    );
  }

  employeeList(
    page: number,
    size: number
  ): Observable<EmployeeList[] | HttpErrorResponse> {
    const reqParams = new HttpParams().set('page', page).set('size', size);
    return this.http.get<EmployeeList[]>(
      `${this.host}${environment.user}${environment.findAll}${environment.employees}`,
      { params: reqParams }
    );
  }

  getEmployeeType(): Observable<EmployeeType[] | HttpErrorResponse> {
    return this.getList(
      `${environment.user}${environment.findAll}${environment.employeeType}`
    );
  }

  resetPassword(email: string): Observable<CustomResponse> {
    const reqParam = new HttpParams().set('email', email);
    return this.http.post<CustomResponse>(
      `${this.host}${environment.user}${environment.resetPassword}`,
      null,
      { params: reqParam }
    );
  }

  updateProfileImage(
    formGroup: FormGroup
  ): Observable<HttpEvent<User | HttpErrorResponse>> {
    return this.http.post<User | HttpErrorResponse>(
      `${this.host}${environment.user}${environment.updateProfileImage}`,
      formGroup,
      {
        reportProgress: true,
        observe: 'events',
      }
    );
  }

  deleteUserById(id: number): Observable<CustomResponse> {
    return this.delete(id, `${environment.user}${environment.delete}`);
  }

  addUsersToLocalCache(users: User[]): void {
    localStorage.setItem('users', JSON.stringify(users));
  }

  getUsersFromLocalCache(): User[] {
    if (localStorage.getItem('users')) {
      return JSON.parse(localStorage.getItem('users'));
    }
    return null;
  }

  count(): Observable<any | HttpErrorResponse> {
    return this.http.get<any>(
      `${this.host}${environment.user}${environment.count}`
    );
  }

  formData(loggedInUsername: string, formGroup: FormGroup): FormData {
    const formData = new FormData();
    formData.append('fullName', formGroup?.value?.fullName);
    formData.append('cellNo', formGroup?.value?.cellNo);
    formData.append('username', formGroup?.value?.username);
    formData.append('email', formGroup?.value?.email);
    formData.append('employee', formGroup?.value?.employee);
    formData.append(
      'itemCategoryId',
      formGroup?.value?.itemCategoryId.join(',')
    );
    formData.append('countryName', formGroup?.value?.countryName);
    formData.append('userType', formGroup?.value?.userType);
    formData.append('role', formGroup?.value?.role);
    formData.append('enabled', JSON.stringify(formGroup?.value?.enabled));
    formData.append(
      'accountLocked',
      JSON.stringify(formGroup?.value?.accountLocked)
    );
    return formData;
  }

  findAll() {
    return this.http.get<AccsUserList[]>(
      `${this.host}${environment.user}${environment.findAll}`
    );
  }

  findAllHREmployees(): Observable<HREmployees[] | HttpErrorResponse> {
    return this.http.get<HREmployees[] | HttpErrorResponse>(
      `${this.host}${environment.user}${environment.hrEmployees}`
    );
  }

  changePassword(
    formGroup: FormGroup
  ): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(
      `${this.host}${environment.user}${environment.changePassword}`,
      formGroup
    );
  }
}
