import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { User } from './user';
import { UserService } from './user.service';
import { ReplaySubject, Subscription, finalize, takeUntil } from 'rxjs';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CustomHttpResponse } from 'src/app/core/classes/custom-http-response';
import { Role } from '../role/role';
import { RoleService } from '../role/role.service';
import { HREmployees } from './hr-employees';
import { AuthService } from 'src/app/core/services/auth.service';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { AccsUserList } from 'src/app/models/accs-auth-user/accs-user-list';
import { CustomResponse } from 'src/app/models/response/custom-response';
import { EmployeeList } from 'src/app/models/accs-auth-user/employee-list';
import { EmployeeType } from 'src/app/models/accs-auth-user/employee-type';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit, OnDestroy {
  userCollection: User[];
  accsUserList: AccsUserList[];
  size: number = 10;
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;
  searchValueForUsername: string = '';
  searchValueForEmail: string = '';
  searchValueForUserType: string = '';
  searchValueForCellNo: string = '';
  subscription: Subscription[] = [];
  constructor(
    private userService: UserService,
    public commonService: CommonService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    public authService: AuthService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnDestroy(): void {
    this.subscription.forEach((sub) => sub.unsubscribe());
  }

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.userList();
    this.commonService.aClickedEvent.subscribe((data: string) => {
      this.userList();
    });
  }

  permission(type: string): boolean {
    return this.authService.permission(environment.user.substring(1), type);
  }

  create() {
    this.dialog.open(CreateEditNewUserDialog, {
      width: '600px',
      data: {
        enabled: true,
        accountLocked: false,
        operation: 'Create',
      },
    });
  }

  edit(collection) {
    console.log(collection, 'user data');
    const itemCategoryIdArray = collection.itemCategoryId
      ? collection.itemCategoryId.split(',').map(Number)
      : [];
    this.dialog.open(CreateEditNewUserDialog, {
      width: '600px',
      data: {
        id: collection.id,
        username: collection.username,
        fullName: collection.fullName,
        cellNo: collection.cellNo,
        email: collection.email,
        employee: collection.employeeId,
        itemCategoryId: itemCategoryIdArray,
        countryName: collection.countryName,
        userType: collection.userTypeId,
        role: collection.role,
        enabled: collection.enabled,
        accountLocked: collection.accountLocked,
        operation: 'Update',
      },
    });
  }

  userList() {
    this.subscription.push(
      this.userService
        .userList(
          this.searchValueForUsername,
          this.searchValueForEmail,
          this.searchValueForUserType,
          this.searchValueForCellNo,
          this.pageOffset,
          this.itemsPerPage
        )
        .subscribe({
          next: (response: any) => {
            // this.count();
            this.accsUserList = response?.content;
            this.totalItems = response?.totalElements;
          },
        })
    );
  }

  delete(id) {
    this.subscription.push(
      this.userService.deleteUserById(id).subscribe({
        next: (response: CustomResponse) => {
          if (response.status === 201 || response.status === 200) {
            this.commonService.AClicked();
            this.notificationService.success(response.message);
          } else {
            this.notificationService.info(response.message);
          }
          // this.count();
        },
        error: (errorResponse: HttpErrorResponse) => {
          this.notificationService.error(errorResponse.error.message);
        },
      })
    );
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.userList();
  }

  searchByUsername(event) {
    this.searchValueForUsername = event;
    this.userList();
  }

  searchByEmail(event) {
    this.searchValueForEmail = event;
    this.userList();
  }

  searchByUserType(event) {
    this.searchValueForUserType = event;
    this.userList();
  }

  searchByCellNo(event) {
    this.searchValueForCellNo = event;
    this.userList();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.userList();
  }

  resetPassword(email) {
    this.subscription.push(
      this.userService.resetPassword(email).subscribe({
        next: (response: CustomResponse) => {
          if (response.status === 200 || response.status === 201) {
            this.notificationService.success(response.message);
          } else {
            this.notificationService.info(response.message);
          }
        },
        error: (errorResponse: HttpErrorResponse) => {
          this.notificationService.error(errorResponse.error.message);
        },
      })
    );
  }
}

@Component({
  templateUrl: './create-edit-new-user.component.html',
  styleUrls: ['./user.component.css'],
})
export class CreateEditNewUserDialog implements OnDestroy, OnInit {
  userGroup: FormGroup;
  defaultImg = './assets/avatar/default.jpg';
  imageUrl = '';
  showRemoveButton = false;
  subscription: Subscription[] = [];
  hrEmployeeList: HREmployees[];
  userCollection: User[];
  roleCollection: Role[];
  employeeCollection: EmployeeList[] = [];
  private originalFile: File;
  isLoading: boolean = false;
  pageOffset = 1;
  itemsPerPage = 6500;

  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private refDialog: MatDialogRef<CreateEditNewUserDialog>,
    public commonService: CommonService,
    private formBuilder: FormBuilder,
    private notificationService: NotificationService,
    private userService: UserService,
    private roleService: RoleService,
    public auth: AuthService
  ) {
    this.userGroup = this.formBuilder.group({
      username: [
        this.data?.username,
        [Validators.required, Validators.minLength(1)],
      ],
      fullName: [this.data?.fullName],
      cellNo: [this.data?.cellNo],
      email: [
        this.data?.email,
        [Validators.email, Validators.required, Validators.minLength(1)],
      ],
      countryName: [this.data?.countryName, Validators.required],
      userType: [this.data?.userType, Validators.required],
      role: [this.data?.role],
      enabled: [this.data?.enabled, [Validators.required]],
      accountLocked: [this.data?.accountLocked, [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.findAllRoles();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
  }

  ngOnDestroy(): void {
    this.subscription.forEach((sub) => sub.unsubscribe());
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  fileUploads(event: any) {
    if (event.target.files && event.target.files[0]) {
      this.originalFile = event.target.files[0];
      const reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (e: any) => {
        this.imageUrl = e.target.result;
        this.showRemoveButton = true;
      };
    }
  }

  basicRemoveImage() {
    this.imageUrl = null;
    this.showRemoveButton = false;
    this.imageUrl = this.defaultImg;
  }

  submit() {
    if (this.data.operation == 'Create') {
      const formData = this.userService.formData(null, this.userGroup);
      this.subscription.push(
        this.userService.addUser(formData).subscribe({
          next: (response: HttpResponse<any>) => {
            this.commonService.AClicked();
            this.notificationService.success('User added successfully!');
            this.refDialog.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            this.notificationService.error(errorResponse.error.message);
          },
        })
      );
    } else {
      const formData = this.userService.formData(
        this.userGroup.value.username,
        this.userGroup
      );
      this.subscription.push(
        this.userService.updateUser(formData).subscribe({
          next: (response: HttpResponse<any>) => {
            this.commonService.AClicked();
            this.notificationService.success('User updated successfully!');
            this.refDialog.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            this.notificationService.error(errorResponse.error.message);
          },
        })
      );
    }
  }

  // findAllHREmployees() {
  //   this.userService.findAllHREmployees().subscribe((data: HREmployees[]) => {
  //     this.hrEmployeeList = data;
  //   });
  // }

  // findAllUsers() {
  //   this.userService.findAll().subscribe((data: User[]) => {
  //     this.userCollection = data;
  //   });
  // }

  findAllRoles() {
    this.roleService.findAll().subscribe((data: Role[]) => {
      this.roleCollection = data;
    });
  }
}
