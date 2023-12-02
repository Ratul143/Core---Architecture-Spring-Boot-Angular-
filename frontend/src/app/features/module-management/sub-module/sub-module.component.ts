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
import { AuthService } from '../../../core/services/auth.service';
import { SubModule } from './sub-module';
import { SubModuleService } from './sub-module.service';
import { Module } from '../module/module';
import { ModuleService } from '../module/module.service';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './sub-module.component.html',
  styleUrls: ['./sub-module.component.css'],
})
export class SubModuleComponent implements OnInit {
  subModuleCollection: SubModule[];
  update: boolean;
  userId: number;
  searchValue: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;

  constructor(
    private subModuleService: SubModuleService,
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
      this.subModuleList();
    });
    this.subModuleList();
  }

  permission(type: string): boolean {
    return this.authService.permission(
      environment.subModule.substring(1),
      type
    );
  }

  subModuleList() {
    this.subModuleService
      .subModuleList(this.searchValue, this.pageOffset, this.itemsPerPage)
      .subscribe({
        next: (response: any) => {
          this.subModuleCollection = response?.content;
          this.totalItems = response?.totalElements;
        },
      });
  }

  delete(id) {
    this.subModuleService.deleteSubModule(id).subscribe({
      next: (response: HttpResponse<any>) => {
        this.commonService.AClicked();
        this.notificationService.success('SubModule Deleted!');
      },
      error: (errorResponse: HttpErrorResponse) => {
        const error = errorResponse.error.message;
        this.notificationService.error(error);
      },
    });
  }

  search(event) {
    this.searchValue = event;
    this.subModuleList();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.subModuleList();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.subModuleList();
  }

  count() {
    this.subModuleService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }

  create() {
    this.dialog.open(SubModuleCreateEditComponent, {
      width: '600px',
      data: {
        operation: 'Create',
      },
    });
  }

  edit(event) {
    this.dialog.open(SubModuleCreateEditComponent, {
      width: '600px',
      data: {
        operation: 'Update',
        collection: event,
      },
    });
  }
}

@Component({
  templateUrl: './sub-module-create-edit-component.html',
  styleUrls: ['./sub-module.component.css'],
})
export class SubModuleCreateEditComponent {
  subModuleGroup: FormGroup;
  operation: string;
  uniqueKey: number;
  moduleCollection: Module[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private refDialog: MatDialogRef<SubModuleCreateEditComponent>,
    private formBuilder: FormBuilder,
    private subModuleService: SubModuleService,
    public commonService: CommonService,
    private notificationService: NotificationService,
    public authService: AuthService,
    private moduleService: ModuleService
  ) {
    this.findAllModules();
    this.operation = data?.operation;
    this.subModuleGroup = this.formBuilder.group({
      module: [
        {
          value: data?.collection?.module?.uniqueKey,
          disabled: this.operation == 'Update',
        },
      ],
      subModuleName: [
        data?.collection?.subModuleName,
        [
          Validators.required,
          Validators.maxLength(30),
          Validators.minLength(1),
        ],
      ],
      // icon: [
      //   data?.collection?.icon,
      //   [
      //     Validators.required,
      //     Validators.maxLength(30),
      //     Validators.minLength(1),
      //   ],
      // ],
      sortOrder: [
        data?.collection?.sortOrder,
        [
          Validators.required,
          Validators.maxLength(30),
          Validators.minLength(1),
        ],
      ],
      isActive: [data?.collection?.isActive],
    });
  }

  submit(): void {
    if (this.operation === 'Update') {
      this.subModuleService
        .updateSubModule(
          this.data?.collection?.uniqueKey,
          this.subModuleGroup.getRawValue()
        )
        .subscribe({
          next: (response: HttpResponse<SubModule>) => {
            this.commonService.AClicked();
            this.resetForm();
            this.notificationService.success('Sub-Module Updated!');
            this.refDialog.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            this.notificationService.error(errorResponse.error.message);
          },
        });
    } else {
      this.subModuleService
        .createSubModule(this.subModuleGroup.getRawValue())
        .subscribe({
          next: (response: HttpResponse<SubModule>) => {
            this.commonService.AClicked();
            this.resetForm();
            this.notificationService.success('Sub-Module Created!');
            this.refDialog.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            this.notificationService.error(errorResponse.error.message);
          },
        });
    }
  }

  resetForm() {
    this.subModuleGroup.reset();
    this.operation = 'Create';
  }

  findAllModules() {
    this.moduleService.findAll().subscribe({
      next: (response: any) => {
        this.moduleCollection = response;
      },
    });
  }
}
