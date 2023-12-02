import { Component, Inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { UserBasedPermissionService } from './user-based-permission.service';
import { AuthService } from '../../../core/services/auth.service';
import { _ComponentService } from '../component/component.service';
import { SubModuleService } from '../sub-module/sub-module.service';
import { ModuleService } from '../module/module.service';
import { UserService } from '../../user-management/user/user.service';
import { User } from '../../user-management/user/user';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { RoleBasedPermissionService } from '../role-based-permission/role-based-permission.service';
import { RoleBasedPermission } from '../role-based-permission/role-based-permission';
import { AccsAuthUser } from 'src/app/models/accs-auth-user/accs-auth-user';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './user-based-permission.component.html',
  styleUrls: ['./user-based-permission.component.css'],
})
export class UserBasedPermissionComponent implements OnInit {
  userCollection: AccsAuthUser[];
  update: boolean;
  userId: number;
  searchValueForUsername: string = '';
  searchValueForEmail: string = '';
  searchValueForUserType: string = '';
  searchValueForCellNo: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;

  constructor(
    private _componentPermissionService: UserBasedPermissionService,
    public commonService: CommonService,
    private matSnackBar: MatSnackBar,
    private router: Router,
    public authService: AuthService,
    private notificationService: NotificationService,
    private moduleService: ModuleService,
    private subModuleService: SubModuleService,
    private _componentService: _ComponentService,
    private dialog: MatDialog,
    private userService: UserService,
    private activatedRoute: ActivatedRoute
  ) {}

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

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.commonService.aClickedEvent.subscribe((data: string) => {
      this.userList();
    });
    this.userList();
  }

  permission(type: string): boolean {
    return this.authService.permission(
      environment.userBasedPermission.substring(1),
      type
    );
  }

  userList() {
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
          this.userCollection = response?.content;
          this.totalItems = response?.totalElements;
        },
      });
  }

  createOrUpdate(collection) {
    this.dialog.open(UserBasedCreateEditPermission, {
      width: '100%',
      height: '90%',
      data: {
        userId: collection?.id,
        roleId: collection?.role?.id,
        operation: 'Update',
      },
    });
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.userList();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.userList();
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }
}

@Component({
  templateUrl: './user-based-permission-create-edit-component.html',
  styleUrls: ['./user-based-permission.component.css'],
})
export class UserBasedCreateEditPermission {
  userBasedPermissionGroup: FormGroup;
  operation: string;
  userCollection: AccsAuthUser[] = [];
  moduleComponentsCollection: any[] = [];
  userId: number;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private refDialog: MatDialogRef<UserBasedCreateEditPermission>,
    private formBuilder: FormBuilder,
    private userBasedPermissionService: UserBasedPermissionService,
    private _componentService: _ComponentService,
    private userService: UserService,
    private subModuleService: SubModuleService,
    public commonService: CommonService,
    private notificationService: NotificationService,
    public authService: AuthService,
    private moduleService: ModuleService,
    private roleBasedPermissionService: RoleBasedPermissionService
  ) {
    this.findAllUsers();
    this.operation = data?.operation;
    this.userId = this.data?.userId;
    this.userBasedPermissionGroup = this.formBuilder.group({
      components: this.formBuilder.array([]),
    });
    this.findModuleComponents();
    setTimeout(() => {
      this.findAllByRoleId(data?.userId, data?.roleId);
    }, 1000);
  }

  get components(): FormArray {
    return this.userBasedPermissionGroup.get('components') as FormArray;
  }

  submit(): void {
    this.userBasedPermissionService
      .createOrUpdateUserBasedPermission(
        this.data.userId,
        this.data?.roleId,
        this.userBasedPermissionGroup.get('components').getRawValue()
      )
      .subscribe({
        next: (response: HttpResponse<RoleBasedPermission>) => {
          this.commonService.AClicked();
          this.notificationService.success('Permission updated!');
          this.refDialog.close();
        },
        error: (errorResponse: HttpErrorResponse) => {
          this.notificationService.error(errorResponse.error.message);
        },
      });
  }

  revertToDefaultRole() {
    console.log(this.userId);
    this.userBasedPermissionService.revertToDefaultRole(this.userId).subscribe({
      next: (response: HttpResponse<any>) => {
        this.commonService.AClicked();
        this.notificationService.success('Permission reverted!');
        this.refDialog.close();
      },
      error: (errorResponse: HttpErrorResponse) => {
        this.notificationService.error(errorResponse.error.message);
      },
    });
  }

  findAllUsers() {
    this.userService.findAll().subscribe({
      next: (response: any) => {
        this.userCollection = response;
      },
    });
  }

  checkBoxOnChange(event) {
    this.components.controls.forEach((elem) => {
      if (event.target.checked) {
        elem.get('check').setValue(event.target.checked);
        elem.get('create').setValue(event.target.checked);
        elem.get('read').setValue(event.target.checked);
        elem.get('update').setValue(event.target.checked);
        elem.get('delete').setValue(event.target.checked);
      } else {
        elem.get('check').setValue(event.target.checked);
        elem.get('create').setValue(event.target.checked);
        elem.get('read').setValue(event.target.checked);
        elem.get('update').setValue(event.target.checked);
        elem.get('delete').setValue(event.target.checked);
      }
    });
  }

  getModuleName(collection) {
    return collection.get('component').value.module.moduleName;
  }

  getComponentName(collection) {
    return collection.get('component').value.componentName;
  }

  getComponentIcon(collection) {
    return collection.get('component').value.icon;
  }

  actionOnChange(component, event) {
    if (event.target.checked) {
      if (
        component.get('create').value ||
        component.get('read').value ||
        component.get('update').value ||
        component.get('delete').value
      ) {
        component.get('check').setValue(true);
      }
    } else {
      if (
        !component.get('create').value &&
        !component.get('read').value &&
        !component.get('update').value &&
        !component.get('delete').value
      ) {
        component.get('check').setValue(false);
      }
    }
  }

  parentCheck(component, event) {
    if (event.target.checked) {
      component.get('create').setValue(event.target.checked);
      component.get('read').setValue(event.target.checked);
      component.get('update').setValue(event.target.checked);
      component.get('delete').setValue(event.target.checked);
    } else {
      component.get('create').setValue(event.target.checked);
      component.get('read').setValue(event.target.checked);
      component.get('update').setValue(event.target.checked);
      component.get('delete').setValue(event.target.checked);
    }
  }

  findModuleComponents() {
    this.moduleService.findModuleComponents().subscribe({
      next: (response: any) => {
        this.moduleComponentsCollection = response;
        response.forEach((elem, i) => {
          elem?.components.forEach((elem2, j) => {
            const componentGroup = this.formBuilder.group({
              check: false,
              component: elem2,
              moduleId: elem2?.module?.id,
              subModuleId: elem2?.subModule?.id,
              componentId: elem2?.id,
              create: false,
              read: false,
              update: false,
              delete: false,
            });
            this.components.push(componentGroup);
          });
        });
      },
    });
  }

  findAllByRoleId(userId, roleId) {
    this.userBasedPermissionService
      .findAllByRoleAndUserId(userId, roleId)
      .subscribe({
        next: (response: any) => {
          this.components.controls.forEach((elem) => {
            response.forEach((resElem) => {
              if (
                elem.value.moduleId == resElem?.module.id &&
                elem.value.componentId == resElem?.component.id
              ) {
                elem.get('check').setValue(true);
                elem.get('create').setValue(resElem?.create);
                elem.get('read').setValue(resElem?.read);
                elem.get('update').setValue(resElem?.update);
                elem.get('delete').setValue(resElem?.delete);
              }
            });
          });
        },
      });
  }
}
