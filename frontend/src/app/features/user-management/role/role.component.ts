import { Component, Inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { Role } from './role';
import { RoleService } from './role.service';
import { AuthService } from '../../../core/services/auth.service';
import { _ComponentService } from '../../module-management/component/component.service';
import { SubModuleService } from '../../module-management/sub-module/sub-module.service';
import { Module } from '../../module-management/module/module';
import { ModuleService } from '../../module-management/module/module.service';
import { UserService } from '../user/user.service';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css'],
})
export class RoleComponent implements OnInit {
  roleCollection: Role[];
  update: boolean;
  userId: number;
  searchValue: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;
  uniqueKey: any;

  constructor(
    private roleService: RoleService,
    public commonService: CommonService,
    private matSnackBar: MatSnackBar,
    private router: Router,
    public authService: AuthService,
    private notificationService: NotificationService,
    private moduleService: ModuleService,
    private subModuleService: SubModuleService,
    private _componentService: _ComponentService,
    private dialog: MatDialog,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.commonService.aClickedEvent.subscribe((data: string) => {
      this.roleList();
    });
    this.roleList();
  }

  permission(type: string): boolean {
    return this.authService.permission(environment.role.substring(1), type);
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

  edit(event) {
    this.uniqueKey = event.uniqueKey;
    this.dialog.open(RoleCreateEditComponent, {
      width: '600px',
      data: {
        operation: 'Update',
        collection: event,
      },
    });
  }

  delete(uniqueKey) {
    this.roleService.deleteRole(uniqueKey).subscribe({
      next: (response: HttpResponse<any>) => {
        this.commonService.AClicked();
        this.notificationService.success('Role Deleted!');
      },
      error: (errorResponse: HttpErrorResponse) => {
        const validationErrors = errorResponse.error.validationErrors;
        if (validationErrors != null) {
          Object.keys(validationErrors).forEach((key) => {
            this.notificationService.error(key);
          });
        }
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

  count() {
    this.roleService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }

  create() {
    this.dialog.open(RoleCreateEditComponent, {
      width: '600px',
      data: {
        operation: 'Create',
      },
    });
  }
}

@Component({
  templateUrl: './role-create-edit-component.html',
  styleUrls: ['./role.component.css'],
})
export class RoleCreateEditComponent {
  roleGroup: FormGroup;
  operation: string;
  moduleCollection: Module[] = [];
  authorities: Array<any> = [
    { name: 'Create', value: 'Create', checked: false },
    { name: 'Read', value: 'Read', checked: false },
    { name: 'Update', value: 'Update', checked: false },
    { name: 'Delete', value: 'Delete', checked: false },
  ];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private refDialog: MatDialogRef<RoleCreateEditComponent>,
    private formBuilder: FormBuilder,
    private roleService: RoleService,
    private _componentService: _ComponentService,
    private userService: UserService,
    private subModuleService: SubModuleService,
    public commonService: CommonService,
    private notificationService: NotificationService,
    public authService: AuthService
  ) {
    this.operation = data?.operation;
    this.roleGroup = this.formBuilder.group({
      role: data?.collection?.role,
      authorities: new FormArray([]),
    });

    this.authorities.forEach((mainElem) => {
      data?.collection?.authorities.forEach((elem) => {
        if (mainElem.value == elem) {
          mainElem.checked = true;
          this.authoritiesArray.push(new FormControl(mainElem.value));
        }
      });
    });
  }

  get authoritiesArray(): FormArray {
    return this.roleGroup.get('authorities') as FormArray;
  }

  submit(): void {
    if (this.operation === 'Update') {
      this.roleService
        .updateRole(
          this.data?.collection?.uniqueKey,
          this.roleGroup.getRawValue()
        )
        .subscribe({
          next: (response: HttpResponse<Role>) => {
            this.commonService.AClicked();
            this.resetForm();
            this.notificationService.success('Role Updated!');
            this.refDialog.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            this.notificationService.error(errorResponse.error.message);
          },
        });
    } else {
      if (!this.roleGroup.value.role.startsWith('ROLE_')) {
        this.notificationService.warning(
          'Add (ROLE_) as a prefix! e.g. ROLE_SUPER_ADMIN'
        );
        return;
      }
      this.roleService.createRole(this.roleGroup.getRawValue()).subscribe({
        next: (response: HttpResponse<Role>) => {
          this.commonService.AClicked();
          this.resetForm();
          this.notificationService.success('Role Created!');
          this.refDialog.close();
        },
        error: (errorResponse: HttpErrorResponse) => {
          this.notificationService.error(errorResponse.error.message);
        },
      });
    }
  }

  resetForm() {
    this.roleGroup.reset();
    this.operation = 'Create';
  }

  authoritiesOnChange(e) {
    if (e.target.checked) {
      this.authoritiesArray.push(new FormControl(e.target.value));
    } else {
      this.authoritiesArray.controls.forEach((item: FormControl, i) => {
        if (item.value == e.target.value) {
          return this.authoritiesArray.removeAt(i);
        }
      });
    }
  }
}
