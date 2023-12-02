import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { _Component } from './component';
import { _ComponentService } from './component.service';
import { AuthService } from '../../../core/services/auth.service';
import { Module } from '../module/module';
import { SubModuleService } from '../sub-module/sub-module.service';
import { SubModule } from '../sub-module/sub-module';
import { ModuleService } from '../module/module.service';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './component.component.html',
  styleUrls: ['./component.component.css'],
})
export class _ComponentComponent implements OnInit {
  _componentCollection: _Component[];
  update: boolean;
  userId: number;
  searchValue: string = '';
  searchPath: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;

  constructor(
    private _componentService: _ComponentService,
    public commonService: CommonService,
    private matSnackBar: MatSnackBar,
    private router: Router,
    public authService: AuthService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.commonService.aClickedEvent.subscribe((data: string) => {
      this._componentList();
    });
    this._componentList();
  }

  permission(type: string): boolean {
    return this.authService.permission(
      environment.component.substring(1),
      type
    );
  }

  _componentList() {
    this._componentService
      ._componentList(
        this.searchValue,
        this.searchPath,
        this.pageOffset,
        this.itemsPerPage
      )
      .subscribe({
        next: (response: any) => {
          this._componentCollection = response?.content;
          this.totalItems = response?.totalElements;
        },
      });
  }

  edit(event) {
    this.dialog.open(_ComponentCreateEditComponent, {
      width: '600px',
      data: {
        operation: 'Update',
        collection: event,
      },
    });
  }

  delete(id) {
    this._componentService.delete_Component(id).subscribe({
      next: (response: HttpResponse<any>) => {
        this.commonService.AClicked();
        this.notificationService.success('Component Deleted!');
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
    this._componentList();
  }

  searchByPath(event) {
    this.searchPath = event;
    this._componentList();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this._componentList();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this._componentList();
  }

  count() {
    this._componentService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }

  create() {
    this.dialog.open(_ComponentCreateEditComponent, {
      width: '600px',
      data: {
        operation: 'Create',
      },
    });
  }
}

@Component({
  templateUrl: './component-create-edit-component.html',
  styleUrls: ['./component.component.css'],
})
export class _ComponentCreateEditComponent {
  _componentGroup: FormGroup;
  operation: string;
  moduleCollection: Module[] = [];
  subModuleCollection: SubModule[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private refDialog: MatDialogRef<_ComponentCreateEditComponent>,
    private formBuilder: FormBuilder,
    private _componentService: _ComponentService,
    private moduleService: ModuleService,
    private subModuleService: SubModuleService,
    public commonService: CommonService,
    private notificationService: NotificationService,
    public authService: AuthService
  ) {
    this.findAllModule();
    if (data) this.findAllSubModuleByModule(data?.collection?.module?.id);
    this.operation = data?.operation;
    this._componentGroup = this.formBuilder.group({
      module: [data?.collection?.module?.id, Validators.required],
      subModule: [data?.collection?.subModule?.uniqueKey],
      component: [
        data?.collection?.componentName,
        [
          Validators.required,
          Validators.maxLength(35),
          Validators.minLength(1),
        ],
      ],
      icon: [
        data?.collection?.icon,
        [
          Validators.required,
          Validators.maxLength(30),
          Validators.minLength(1),
        ],
      ],
      path: [
        data?.collection?.path,
        [
          Validators.required,
          Validators.maxLength(50),
          Validators.minLength(1),
        ],
      ],
      sortOrder: [
        data?.collection?.sortOrder,
        [Validators.required, Validators.maxLength(5), Validators.minLength(1)],
      ],
      isActive: [data?.collection?.isActive ?? true],
    });
    this._componentGroup.get('module').valueChanges.subscribe((data) => {
      if (data) this.findAllSubModuleByModule(data);
    });
  }

  submit(): void {
    console.log(this._componentGroup.getRawValue());

    if (this.operation === 'Update') {
      this._componentService
        .update_Component(
          this.data?.collection?.uniqueKey,
          this._componentGroup.getRawValue()
        )
        .subscribe({
          next: (response: HttpResponse<_Component>) => {
            this.commonService.AClicked();
            this.resetForm();
            this.notificationService.success('Component Updated!');
            this.refDialog.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            this.notificationService.error(errorResponse.error.message);
          },
        });
    } else {
      this._componentService
        .create_Component(this._componentGroup.getRawValue())
        .subscribe({
          next: (response: HttpResponse<_Component>) => {
            this.commonService.AClicked();
            this.resetForm();
            this.notificationService.success('Component Created!');
            this.refDialog.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            this.notificationService.error(errorResponse.error.message);
          },
        });
    }
  }

  resetForm() {
    this._componentGroup.reset();
    this.operation = 'Create';
  }

  findAllModule() {
    this.moduleService.findAll().subscribe({
      next: (response: any) => {
        this.moduleCollection = response;
      },
    });
  }

  findAllSubModuleByModule(moduleId) {
    this.subModuleService.findAllSubModulesByModuleId(moduleId).subscribe({
      next: (response: any) => {
        this.subModuleCollection = response;
      },
    });
  }
}
