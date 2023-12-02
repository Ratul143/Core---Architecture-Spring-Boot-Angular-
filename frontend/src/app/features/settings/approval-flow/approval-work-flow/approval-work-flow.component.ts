import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ApprovalWorkFlowStatusService } from '../approval-work-flow-status/approval-work-flow-status.service';
import { ApprovalWorkFlowStatus } from '../approval-work-flow-status/approval-work-flow-status';
import { ApprovalWorkFlow } from './approval-work-flow';
import { ApprovalWorkFlowService } from './approval-work-flow.service';
import { ApprovalWorkFlowFor } from '../approval-work-flow-for/approval-work-flow-for';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { ApprovalWorkFlowForService } from '../approval-work-flow-for/approval-work-flow-for.service';
import { UserFormApprovalFlowService } from '../user-form-approval-flow/user-form-approval-flow.service';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UserService } from 'src/app/features/user-management/user/user.service';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './approval-work-flow.component.html',
  styleUrls: ['./approval-work-flow.component.css'],
})
export class ApprovalWorkFlowComponent implements OnInit {
  approvalWorkFlowCollection: ApprovalWorkFlow[];
  update: boolean;
  userId: number;
  searchValueForForm: string = '';
  searchValueForStatus: string = '';
  searchValueForCode: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;

  constructor(
    private approvalWorkFlowService: ApprovalWorkFlowService,
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
      environment.approvalWorkFlow.substring(1),
      type
    );
  }

  approvalWorkFlowForList() {
    this.approvalWorkFlowService
      .approvalWorkFlowForList(
        this.searchValueForForm,
        this.searchValueForStatus,
        this.searchValueForCode,
        this.pageOffset,
        this.itemsPerPage
      )
      .subscribe({
        next: (response: ApprovalWorkFlow[]) => {
          this.approvalWorkFlowCollection = response;
          if (!response.length) this.totalItems = 0;
        },
        error: (errorResponse: HttpErrorResponse) => {
          const errors = errorResponse.error;
        },
      });
    this.count();
  }

  delete(event) {
    this.approvalWorkFlowService.deleteApprovalWorkFlow(event.id).subscribe({
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

  searchByForm(event) {
    this.searchValueForForm = event;
    this.approvalWorkFlowForList();
  }

  searchByStatus(event) {
    this.searchValueForStatus = event;
    this.approvalWorkFlowForList();
  }

  searchByCode(event) {
    this.searchValueForCode = event;
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
    this.approvalWorkFlowService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }

  add() {
    this.dialog.open(ApprovalFlowCreateEditComponent, {
      width: '600px',
      height: '400px',
      data: {
        operation: 'Create',
      },
    });
  }

  edit(collection) {
    this.dialog.open(ApprovalFlowCreateEditComponent, {
      width: '600px',
      height: '400px',
      data: {
        approvalWorkFlowId: collection?.id,
        flowFor: collection?.approvalWorkFlowFor?.id,
        status: collection?.approvalWorkFlowStatus?.id,
        statusLevel: collection?.statusLevel,
        isFinalLevel: collection?.isFinalLevel,
        isActive: collection?.isActive,
        operation: 'Update',
      },
    });
  }
}

@Component({
  templateUrl: './approval-flow-create-edit.component.html',
})
export class ApprovalFlowCreateEditComponent {
  approvalWorkFlowGroup: FormGroup;
  operation: string;
  approvalWorkFlowForCollection: ApprovalWorkFlowFor[] = [];
  approvalWorkFlowStatusCollection: ApprovalWorkFlowStatus[];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private notificationService: NotificationService,
    private approvalWorkFlowForService: ApprovalWorkFlowForService,
    private userFormApprovalFlowService: UserFormApprovalFlowService,
    public dialogRef: MatDialogRef<ApprovalFlowCreateEditComponent>,
    public commonService: CommonService,
    private approvalWorkFlowService: ApprovalWorkFlowService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private approvalWorkFlowStatusService: ApprovalWorkFlowStatusService
  ) {
    this.operation = data?.operation;
    console.log(data);
    this.findAllApprovalWorkFlow();
    this.findAllAvailableStatus();
    this.approvalWorkFlowGroup = this.formBuilder.group({
      flowFor: [data?.flowFor, Validators.required],
      status: [data?.status, Validators.required],
      statusLevel: [data?.statusLevel, Validators.required],
      isFinalLevel: [data?.isFinalLevel],
      isActive: [data?.isActive],
    });
  }

  findAllApprovalWorkFlow() {
    this.approvalWorkFlowForService.findAll().subscribe({
      next: (response: any) => {
        this.approvalWorkFlowForCollection = response;
      },
    });
  }

  findAllAvailableStatus() {
    this.approvalWorkFlowStatusService.findAll().subscribe({
      next: (response: any) => {
        this.approvalWorkFlowStatusCollection = response;
      },
    });
  }

  submit(): void {
    if (this.operation === 'Update') {
      this.approvalWorkFlowService
        .updateApprovalWorkFlow(
          this.data?.approvalWorkFlowId,
          this.approvalWorkFlowGroup.getRawValue()
        )
        .subscribe({
          next: (response: HttpResponse<ApprovalWorkFlow>) => {
            this.commonService.AClicked();
            this.notificationService.success('Approval Work Flow Updated!');
            this.dialogRef.close();
          },
          error: (errorResponse: HttpErrorResponse) => {
            const error = errorResponse.error.message;
            this.notificationService.error(error);
          },
        });
    } else {
      this.approvalWorkFlowService
        .createApprovalWorkFlow(this.approvalWorkFlowGroup.getRawValue())
        .subscribe({
          next: (response: HttpResponse<ApprovalWorkFlow>) => {
            this.commonService.AClicked();
            this.notificationService.success('Approval Work Flow Created!');
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
}
