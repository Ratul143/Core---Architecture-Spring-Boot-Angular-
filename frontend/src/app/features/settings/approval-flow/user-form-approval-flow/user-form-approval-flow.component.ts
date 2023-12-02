import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { ApprovalWorkFlowFor } from '../approval-work-flow-for/approval-work-flow-for';
import { ApprovalWorkFlowForService } from '../approval-work-flow-for/approval-work-flow-for.service';
import { UserFormApprovalFlow } from './user-form-approval-flow';
import { UserFormApprovalFlowService } from './user-form-approval-flow.service';
import { ApprovalWorkFlowService } from '../approval-work-flow/approval-work-flow.service';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UserService } from 'src/app/features/user-management/user/user.service';
import { User } from 'src/app/features/user-management/user/user';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './user-form-approval-flow.component.html',
  styleUrls: ['./user-form-approval-flow.component.css'],
})
export class UserFormApprovalFlowComponent implements OnInit {
  userFormApprovalFlowCollection: UserFormApprovalFlow[];
  update: boolean;
  userId: number;
  searchValue: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;
  searchValueForFormName: string = '';
  searchValueForUser: string = '';
  searchValueForStatus: string = '';

  constructor(
    private userFormApprovalFlowService: UserFormApprovalFlowService,
    public commonService: CommonService,
    private matSnackBar: MatSnackBar,
    private formBuilder: FormBuilder,
    private router: Router,
    public authService: AuthService,
    private notificationService: NotificationService,
    public dialog: MatDialog,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.commonService.aClickedEvent.subscribe((data: string) => {
      this.findUserFormApprovalFlowList();
    });
    this.findUserFormApprovalFlowList();
  }

  permission(type: string): boolean {
    return this.authService.permission(
      environment.userFormApprovalFlow.substring(1),
      type
    );
  }

  findUserFormApprovalFlowList() {
    this.userFormApprovalFlowService
      .findApprovalFlowStepsList(
        this.searchValueForFormName,
        this.searchValueForUser,
        this.searchValueForStatus,
        this.pageOffset,
        this.itemsPerPage
      )
      .subscribe({
        next: (response: UserFormApprovalFlow[]) => {
          this.userFormApprovalFlowCollection = response;
          this.count();
          if (!response.length) this.totalItems = 0;
        },
        error: (errorResponse: HttpErrorResponse) => {
          const errors = errorResponse.error;
        },
      });
  }

  delete(event) {
    this.userFormApprovalFlowService
      .deleteApprovalWorkFlowSteps(event.id)
      .subscribe({
        next: (response: HttpResponse<any>) => {
          this.commonService.AClicked();
          this.notificationService.success('User Permission Deleted!');
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

  searchByFormName(event) {
    this.searchValueForFormName = event;
    this.findUserFormApprovalFlowList();
  }

  searchByUser(event) {
    this.searchValueForUser = event;
    this.findUserFormApprovalFlowList();
  }

  searchByStepsStatus(event) {
    this.searchValueForStatus = event;
    this.findUserFormApprovalFlowList();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.findUserFormApprovalFlowList();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.findUserFormApprovalFlowList();
  }

  count() {
    this.userFormApprovalFlowService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }

  add() {
    this.dialog.open(UserFormApprovalFlowCreateEditComponent, {
      width: '600px',
      height: '400px',
      data: {
        operation: 'Create',
      },
    });
  }

  edit(collection) {
    this.dialog.open(UserFormApprovalFlowCreateEditComponent, {
      width: '600px',
      height: '400px',
      data: {
        updateUserFormApprovalFlowId: collection?.id,
        form: collection?.approvalWorkFlowFor?.id,
        status: collection?.approvalWorkFlow?.approvalWorkFlowStatus?.id,
        user: collection?.user?.id,
        operation: 'Update',
      },
    });
  }
}

@Component({
  templateUrl: './user-form-approval-flow-create-edit.component.html',
})
export class UserFormApprovalFlowCreateEditComponent implements OnInit {
  userFormApprovalFlowGroup: FormGroup;
  operation: string;
  approvalWorkFlowForCollection: ApprovalWorkFlowFor[] = [];
  approvalWorkFlowStatusCollection: UserFormApprovalFlow[] = [];
  userListCollection: User[] = [];
  updateUserFormApprovalFlowId: number;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private notificationService: NotificationService,
    private approvalWorkFlowForService: ApprovalWorkFlowForService,
    private userFormApprovalFlowService: UserFormApprovalFlowService,
    public dialogRef: MatDialogRef<UserFormApprovalFlowCreateEditComponent>,
    public commonService: CommonService,
    private approvalWorkFlowService: ApprovalWorkFlowService,
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {
    this.operation = data?.operation;
    this.findAllForms();
    this.findAllStatusByForm(data?.form);
    this.findAllUser();
    this.userFormApprovalFlowGroup = this.formBuilder.group({
      form: [data?.form, Validators.required],
      status: [data?.status, Validators.required],
      user: [data?.user, Validators.required],
    });
  }

  ngOnInit(): void {
    this.userFormApprovalFlowGroup
      .get('form')
      .valueChanges.subscribe((data) => {
        if (data) this.findAllStatusByForm(data);
      });
    this.commonService.scrollTop();
  }

  findAllForms() {
    this.approvalWorkFlowForService.findAll().subscribe({
      next: (response: any) => {
        this.approvalWorkFlowForCollection = response;
      },
    });
  }

  findAllStatusByForm(event) {
    this.approvalWorkFlowService.findApprovalFlowStatusByForm(event).subscribe({
      next: (response: any) => {
        this.approvalWorkFlowStatusCollection = response;
      },
    });
  }

  findAllUser() {
    this.userService.findAll().subscribe({
      next: (response: any) => {
        this.userListCollection = response;
      },
    });
  }

  submit(): void {
    if (this.operation === 'Update') {
      this.userFormApprovalFlowService
        .updateUserFormApprovalFlow(
          this.data?.updateUserFormApprovalFlowId,
          this.userFormApprovalFlowGroup.getRawValue()
        )
        .subscribe({
          next: (response: HttpResponse<UserFormApprovalFlow>) => {
            this.notificationService.success(
              'User Form Approval Flow Updated!'
            );
            this.commonService.AClicked();
            this.dialogRef.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            const error = errorResponse.error.message;
            this.notificationService.error(error);
          },
        });
    } else {
      this.userFormApprovalFlowService
        .createUserFormApprovalFlow(
          this.userFormApprovalFlowGroup.getRawValue()
        )
        .subscribe({
          next: (response: HttpResponse<UserFormApprovalFlow>) => {
            this.notificationService.success(
              'User Form Approval Flow Created!'
            );
            this.commonService.AClicked();
            this.dialogRef.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            const error = errorResponse.error.message;
            this.notificationService.error(error);
          },
        });
    }
  }
}
