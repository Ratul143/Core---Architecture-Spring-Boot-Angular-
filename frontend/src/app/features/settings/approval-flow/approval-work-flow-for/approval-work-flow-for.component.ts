import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ApprovalWorkFlowFor } from './approval-work-flow-for';
import { ApprovalWorkFlowForService } from './approval-work-flow-for.service';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { UserFormApprovalFlowService } from '../user-form-approval-flow/user-form-approval-flow.service';
import { ApprovalWorkFlowService } from '../approval-work-flow/approval-work-flow.service';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UserService } from 'src/app/features/user-management/user/user.service';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './approval-work-flow-for.component.html',
  styleUrls: ['./approval-work-flow-for.component.css'],
})
export class ApprovalWorkFlowForComponent implements OnInit {
  approvalWorkFlowForGroupCollection: ApprovalWorkFlowFor[];
  searchValueForApprovalWorkFlowFormName: string = '';
  searchValueForApprovalWorkFlowFormCode: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;

  constructor(
    private approvalWorkFlowForService: ApprovalWorkFlowForService,
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
      this.approvalWorkFlowForList();
    });
    this.approvalWorkFlowForList();
  }

  permission(type: string): boolean {
    return this.authService.permission(
      environment.approvalWorkFlowForm.substring(1),
      type
    );
  }

  approvalWorkFlowForList() {
    this.approvalWorkFlowForService
      .approvalWorkFlowForList(
        this.searchValueForApprovalWorkFlowFormName,
        this.searchValueForApprovalWorkFlowFormCode,
        this.pageOffset,
        this.itemsPerPage
      )
      .subscribe({
        next: (response: ApprovalWorkFlowFor[]) => {
          this.approvalWorkFlowForGroupCollection = response;
          if (!response.length) this.totalItems = 0;
        },
        error: (errorResponse: HttpErrorResponse) => {
          const errors = errorResponse.error;
        },
      });
    this.count();
  }

  delete(event) {
    this.approvalWorkFlowForService
      .deleteApprovalWorkFlowFor(event.id)
      .subscribe({
        next: (response: HttpResponse<any>) => {
          this.commonService.AClicked();
          this.notificationService.success('Form Deleted!');
          this.count();
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

  searchByApprovalWorkFlowFormName(event) {
    this.searchValueForApprovalWorkFlowFormName = event;
    this.approvalWorkFlowForList();
  }

  searchByApprovalWorkFlowFormCode(event) {
    this.searchValueForApprovalWorkFlowFormCode = event;
    this.approvalWorkFlowForList();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.approvalWorkFlowForList();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.approvalWorkFlowForList();
  }

  count() {
    this.approvalWorkFlowForService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }

  add() {
    this.dialog.open(ApprovalWorkFlowForCreateEditComponent, {
      width: '600px',
      height: '400px',
      data: {
        operation: 'Create',
      },
    });
  }

  edit(collection) {
    this.dialog.open(ApprovalWorkFlowForCreateEditComponent, {
      width: '600px',
      height: '400px',
      data: {
        formId: collection?.id,
        flowFor: collection?.flowFor,
        code: collection?.code,
        operation: 'Update',
      },
    });
  }
}

@Component({
  templateUrl: './approval-work-flow-for-create-edit.component.html',
  styleUrls: ['./approval-work-flow-for.component.css'],
})
export class ApprovalWorkFlowForCreateEditComponent {
  approvalWorkFlowForGroup: FormGroup;
  operation: string;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private notificationService: NotificationService,
    private approvalWorkFlowForService: ApprovalWorkFlowForService,
    private userFormApprovalFlowService: UserFormApprovalFlowService,
    public dialogRef: MatDialogRef<ApprovalWorkFlowForCreateEditComponent>,
    public commonService: CommonService,
    private approvalWorkFlowService: ApprovalWorkFlowService,
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {
    this.operation = data?.operation;
    this.approvalWorkFlowForGroup = this.formBuilder.group({
      flowFor: [data?.flowFor, Validators.required],
      code: [
        { value: data?.code, disabled: this.operation == 'Update' },
        Validators.required,
      ],
    });
  }

  submit(): void {
    if (this.operation === 'Update') {
      this.approvalWorkFlowForService
        .updateApprovalWorkFlowFor(
          this.data?.formId,
          this.approvalWorkFlowForGroup.getRawValue()
        )
        .subscribe({
          next: (response: HttpResponse<ApprovalWorkFlowFor>) => {
            this.commonService.AClicked();
            this.notificationService.success('Form Updated!');
            this.dialogRef.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            const error = errorResponse.error.message;
            this.notificationService.error(error);
          },
        });
    } else {
      this.approvalWorkFlowForService
        .createApprovalWorkFlowFor(this.approvalWorkFlowForGroup.getRawValue())
        .subscribe({
          next: (response: HttpResponse<ApprovalWorkFlowFor>) => {
            this.commonService.AClicked();
            this.notificationService.success('Form Created!');
            this.dialogRef.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            const error = errorResponse.error.message;
            this.notificationService.error(error);
          },
        });
      return;
    }
  }

  resetForm() {
    this.operation = 'Create';
    return this.approvalWorkFlowForGroup.reset();
  }
}
