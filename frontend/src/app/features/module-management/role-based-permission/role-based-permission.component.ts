import { Component, Inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { RoleBasedPermission } from './role-based-permission';
import { RoleBasedPermissionService } from './role-based-permission.service';
import { AuthService } from '../../../core/services/auth.service';
import { Module } from '../module/module';
import { SubModuleService } from '../sub-module/sub-module.service';
import { ModuleService } from '../module/module.service';
import { RoleService } from '../../user-management/role/role.service';
import { Role } from '../../user-management/role/role';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { environment } from 'src/environments/environment';
import { SubModule } from '../sub-module/sub-module';
import { _Component } from '../component/component';
import { Subject, debounceTime, distinctUntilChanged } from 'rxjs';
import { FILTER, SEARCH, SELECT } from 'src/app/core/constants/constants';
import { _ComponentService } from '../component/component.service';

@Component({
  templateUrl: './role-based-permission.component.html',
  styleUrls: ['./role-based-permission.component.css'],
})
export class RoleBasedPermissionComponent implements OnInit {
  roleCollection: Role[];
  update: boolean;
  userId: number;
  searchValue: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;

  constructor(
    private moduleForRoleService: RoleBasedPermissionService,
    public commonService: CommonService,
    private matSnackBar: MatSnackBar,
    private router: Router,
    public authService: AuthService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    private roleService: RoleService
  ) {}

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.commonService.aClickedEvent.subscribe((data: string) => {
      this.roleList();
    });
    this.roleList();
  }

  permission(type: string): boolean {
    return this.authService.permission(
      environment.roleBasedPermission.substring(1),
      type
    );
  }

  roleList() {
    this.roleService
      .roleList(this.searchValue, this.pageOffset, this.itemsPerPage)
      .subscribe({
        next: (response: any) => {
          this.roleCollection = response?.content;
          this.totalItems = response?.totalElements;
        },
      });
  }

  createOrEdit(collection) {
    this.dialog.open(ModuleForRoleCreateEditComponent, {
      width: '100%',
      height: '90%',
      data: {
        roleUniqueKey: collection?.uniqueKey,
        operation: 'Update',
      },
    });
  }

  search(event) {
    this.searchValue = event;
    this.roleList();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.roleList();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.roleList();
  }
  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }
}

@Component({
  templateUrl: './role-based-permission-create-edit-component.html',
  styleUrls: ['./role-based-permission.component.css'],
})
export class ModuleForRoleCreateEditComponent implements OnInit {
  roleBasedPermissionGroup: FormGroup;
  operation: string;
  uniqueKey: string;
  moduleUniqueKey: string = '';
  subModuleUniqueKey: string = '';
  componentUniqueKey: string = '';
  searchByAny: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;

  moduleCollection: Module[];
  subModuleCollection: SubModule[];
  componentCollection: _Component[];

  protected readonly SELECT = SELECT;
  protected readonly SEARCH = SEARCH;
  protected readonly FILTER = FILTER;
  private moduleSubject = new Subject<string>();
  private subModuleSubject = new Subject<string>();
  private componentSubject = new Subject<string>();
  private anySearchSubject = new Subject<string>();

  get moduleArrayControl(): FormArray {
    return this.roleBasedPermissionGroup.get('modules') as FormArray;
  }

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private refDialog: MatDialogRef<ModuleForRoleCreateEditComponent>,
    private formBuilder: FormBuilder,
    private roleBasedPermissionService: RoleBasedPermissionService,
    private moduleService: ModuleService,
    private subModuleService: SubModuleService,
    private _componentService: _ComponentService,
    public commonService: CommonService,
    private notificationService: NotificationService,
    public authService: AuthService
  ) {
    this.findAllModuleByKeyword(null);
    this.roleBasedPermissionGroup = this.formBuilder.group({
      modules: this.formBuilder.array([]),
    });
    this.operation = data?.operation;
  }

  searchByAnyValue(event) {
    this.searchByAny = event;
    this.anySearchSubject.next(event);
  }

  modulesArray() {
    return this.formBuilder.group({
      uniqueKey: '',
      moduleUniqueKey: '',
      subModuleUniqueKey: '',
      componentUniqueKey: '',
      create: false,
      read: false,
      update: false,
      delete: false,
    });
  }

  addRow() {
    this.moduleArrayControl.insert(0, this.modulesArray());
  }

  submit(): void {
    this.roleBasedPermissionService
      .createOrUpdateRoleBasedPermission(
        this.data?.roleUniqueKey,
        this.roleBasedPermissionGroup.get('modules').getRawValue()
      )
      .subscribe({
        next: (response: HttpResponse<RoleBasedPermission>) => {
          this.commonService.refreshAsideClick();
          this.commonService.AClicked();
          this.notificationService.success('Permission updated!');
          this.refDialog.close();
        },
        error: (errorResponse: HttpErrorResponse) => {
          this.notificationService.error(errorResponse.error.message);
        },
      });
  }

  resetForm() {
    this.roleBasedPermissionGroup.reset();
    this.moduleArrayControl.clear();
    this.operation = 'Create';
  }

  trackByFn(index: number, item: any): any {
    return item.value;
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.findAllByRoleUniqueKey();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.findAllByRoleUniqueKey();
  }

  ngOnInit(): void {
    this.findAllByRoleUniqueKey();

    this.moduleSubject
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((value) => {
        this.findAllModuleByKeyword(value);
        this.findAllByRoleUniqueKey();
      });

    this.subModuleSubject
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((value: any) => {
        this.findAllSubModuleByParentUniqueKeyAndKeyword(value, null);
        this.findAllByRoleUniqueKey();
      });

    this.componentSubject
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((value: any) => {
        this.findAllComponentByParentUniqueKeyAndKeyword(value, null);
        this.findAllByRoleUniqueKey();
      });

    this.anySearchSubject
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((value: any) => {
        this.findAllByRoleUniqueKey();
      });
  }

  findAllByRoleUniqueKey() {
    this.roleBasedPermissionService
      .findAllByRoleUniqueKey(
        this.data?.roleUniqueKey,
        this.moduleUniqueKey,
        this.subModuleUniqueKey,
        this.componentUniqueKey,
        this.searchByAny,
        this.pageOffset,
        this.itemsPerPage
      )
      .subscribe({
        next: (response: any) => {
          this.moduleArrayControl.clear();
          response?.content?.forEach((elem, i) => {
            this.moduleArrayControl.push(
              this.formBuilder.group({
                uniqueKey: elem?.uniqueKey ?? 'N/A',
                moduleName: elem?.module?.moduleName ?? 'N/A',
                subModuleName: elem?.subModule?.subModuleName ?? 'N/A',
                componentName: elem?.component?.componentName ?? 'N/A',
                create: elem?.create,
                read: elem?.read,
                update: elem?.update,
                delete: elem?.delete,
              })
            );
          });
          this.totalItems = response?.totalElements;
        },
      });
  }

  checkIfRowExists() {
    return this.moduleArrayControl.length == 0;
  }

  moduleOnChange(value, type, filter) {
    if (type == SEARCH && value.length > 0) {
      this.moduleSubject.next(value);
    } else if (type == SELECT && filter != null) {
      this.subModuleCollection = [];
      this.findAllSubModuleByParentUniqueKeyAndKeyword(value, null);
      this.moduleUniqueKey = value;
      this.findAllByRoleUniqueKey();
    } else if (type == SELECT && filter == null) {
      this.moduleUniqueKey = value;
      this.findAllSubModuleByParentUniqueKeyAndKeyword(value, null);
    }
  }

  subModuleOnChange(value, type, filter) {
    if (type == SEARCH && value.length > 0) {
      this.subModuleSubject.next(value);
    } else if (type == SELECT && filter != null) {
      this.componentCollection = [];
      this.findAllComponentByParentUniqueKeyAndKeyword(value, null);
      this.subModuleUniqueKey = value;
      this.findAllByRoleUniqueKey();
    } else if (type == SELECT && filter == null) {
      this.findAllComponentByParentUniqueKeyAndKeyword(value, null);
    }
  }

  componentOnChange(value, type, filter) {
    if (type == SEARCH && value.length > 0) {
      this.componentSubject.next(value);
    } else if (type == SELECT && filter != null) {
      this.componentUniqueKey = value;
      this.findAllByRoleUniqueKey();
    }
  }

  findAllModuleByKeyword(keyword) {
    this.moduleService.findAllModuleByKeyword(keyword).subscribe({
      next: (response: Module[]) => {
        this.moduleCollection = response;
      },
    });
  }

  async findAllSubModuleByParentUniqueKeyAndKeyword(parentUniqueKey, keyword) {
    this.subModuleService
      .findAllByParentUniqueKeyAndKeyword(parentUniqueKey, keyword)
      .subscribe({
        next: (response: SubModule[]) => {
          this.subModuleCollection = response;
          this.findAllComponentByModuleUniqueKeyAndKeyword(null);
          // if (this.subModuleCollection == null || this.subModuleCollection.length == 0) {
          // }
        },
      });
  }

  findAllComponentByParentUniqueKeyAndKeyword(parentUniqueKey, keyword) {
    this._componentService
      .findAllComponentBySubModuleUniqueKeyAndKeyword(parentUniqueKey, keyword)
      .subscribe({
        next: (response: _Component[]) => {
          this.componentCollection = response;
        },
      });
  }

  findAllComponentByModuleUniqueKeyAndKeyword(keyword) {
    this._componentService
      .findAllComponentByModuleUniqueKeyAndKeyword(
        this.moduleUniqueKey,
        keyword
      )
      .subscribe({
        next: (response: _Component[]) => {
          this.componentCollection = response;
        },
      });
  }

  actionOnChange(component, event) {
    if (event.target.checked) {
      setTimeout(() => {
        if (
          component.get('create').value ||
          component.get('read').value ||
          component.get('update').value ||
          component.get('delete').value
        ) {
          component.get('check').setValue(true);
        }
      }, 0);
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

  onCreate(i) {
    return this.moduleArrayControl.at(i).getRawValue()?.moduleName;
  }
}
