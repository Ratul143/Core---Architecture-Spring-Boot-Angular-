import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Module } from './module';
import { ModuleService } from './module.service';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { AuthService } from '../../../core/services/auth.service';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './module.component.html',
  styleUrls: ['./module.component.css'],
})
export class ModuleComponent implements OnInit {
  moduleCollection: Module[];
  update: boolean;
  userId: number;
  searchValue: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;

  constructor(
    private moduleService: ModuleService,
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
      this.moduleList();
    });
    this.moduleList();
  }

  permission(type: string): boolean {
    return this.authService.permission(environment.module.substring(1), type);
  }

  moduleList() {
    this.moduleService
      .moduleList(this.searchValue, this.pageOffset, this.itemsPerPage)
      .subscribe({
        next: (response: any) => {
          this.moduleCollection = response?.content;
          this.totalItems = response?.totalElements;
        },
      });
  }

  delete(id) {
    this.moduleService.deleteModule(id).subscribe({
      next: (response: HttpResponse<any>) => {
        this.commonService.AClicked();
        this.notificationService.success('Module Deleted!');
      },
      error: (errorResponse: HttpErrorResponse) => {
        const error = errorResponse.error.message;
        this.notificationService.error(error);
      },
    });
  }

  search(event) {
    this.searchValue = event;
    this.moduleList();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.moduleList();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.moduleList();
  }

  count() {
    this.moduleService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }

  create() {
    this.dialog.open(ModuleCreateEditComponent, {
      width: '600px',
      data: {
        operation: 'Create',
      },
    });
  }

  edit(event) {
    this.dialog.open(ModuleCreateEditComponent, {
      width: '600px',
      data: {
        operation: 'Update',
        collection: event,
      },
    });
  }
}

@Component({
  templateUrl: './module-create-edit-component.html',
  styleUrls: ['./module.component.css'],
})
export class ModuleCreateEditComponent {
  moduleGroup: FormGroup;
  operation: string;
  uniqueKey: number;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private refDialog: MatDialogRef<ModuleCreateEditComponent>,
    private formBuilder: FormBuilder,
    private moduleService: ModuleService,
    public commonService: CommonService,
    private notificationService: NotificationService,
    public authService: AuthService
  ) {
    this.moduleGroup = this.formBuilder.group({
      module: [
        data?.collection?.moduleName,
        [
          Validators.required,
          Validators.maxLength(30),
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
      sortOrder: [
        data?.collection?.sortOrder,
        [
          Validators.required,
          Validators.maxLength(30),
          Validators.minLength(1),
        ],
      ],
      isActive: [data?.collection?.isActive ?? true],
    });
    this.operation = data?.operation;
    this.uniqueKey = data?.collection?.uniqueKey;
  }

  submit(): void {
    if (this.operation === 'Update') {
      this.moduleService
        .updateModule(this.uniqueKey, this.moduleGroup.value)
        .subscribe({
          next: (response: HttpResponse<Module>) => {
            this.commonService.AClicked();
            this.resetForm();
            this.notificationService.success('Module Updated!');
            this.refDialog.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            this.notificationService.error(errorResponse.error.message);
          },
        });
    } else {
      this.moduleService.createModule(this.moduleGroup.value).subscribe({
        next: (response: HttpResponse<Module>) => {
          this.commonService.AClicked();
          this.resetForm();
          this.notificationService.success('Module Created!');
          this.refDialog.close();
        },
        error: (errorResponse: HttpErrorResponse) => {
          this.notificationService.error(errorResponse.error.message);
        },
      });
    }
  }

  resetForm() {
    this.moduleGroup.reset();
    this.operation = 'Create';
  }
}
