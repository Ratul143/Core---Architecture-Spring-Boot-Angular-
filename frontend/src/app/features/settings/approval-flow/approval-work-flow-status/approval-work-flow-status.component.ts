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
import { ApprovalWorkFlowStatus } from './approval-work-flow-status';
import { ApprovalWorkFlowStatusService } from './approval-work-flow-status.service';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-composition-of-rm',
  templateUrl: './approval-work-flow-status.component.html',
  styleUrls: ['./approval-work-flow-status.component.css'],
})
export class ApprovalWorkFlowStatusComponent implements OnInit {
  compositionGroup: FormGroup;
  approvalWorkFlowStatusCollection: ApprovalWorkFlowStatus[];
  update: boolean;
  userId: number;
  searchValue: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;
  searchValueForStatusName: string = '';
  searchValueForStatusCode: string = '';

  constructor(
    private approvalWorkFlowStatusService: ApprovalWorkFlowStatusService,
    public commonService: CommonService,
    private matSnackBar: MatSnackBar,
    private formBuilder: FormBuilder,
    private router: Router,
    public authService: AuthService,
    private notificationService: NotificationService,
    public dialog: MatDialog,
    private activatedRoute: ActivatedRoute
  ) {
    this.compositionGroup = this.formBuilder.group({
      composition: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.commonService.aClickedEvent.subscribe((data: string) => {
      this.getApprovalWorkFlowStatus();
    });
    this.getApprovalWorkFlowStatus();
  }

  permission(type: string): boolean {
    return this.authService.permission(
      environment.approvalWorkFlowStatus.substring(1),
      type
    );
  }

  getApprovalWorkFlowStatus() {
    this.approvalWorkFlowStatusService
      .findApprovalFlowStatusList(
        this.searchValueForStatusName,
        this.searchValueForStatusCode,
        this.pageOffset,
        this.itemsPerPage
      )
      .subscribe({
        next: (response: []) => {
          this.approvalWorkFlowStatusCollection = response;
          this.count();
          if (!response.length) this.totalItems = 0;
        },
        error: (errorResponse: HttpErrorResponse) => {
          const errors = errorResponse.error;
        },
      });
    this.count();
  }

  resetForm() {
    this.compositionGroup.reset();
    this.update = false;
  }

  delete(event) {
    this.approvalWorkFlowStatusService
      .deleteApprovalWorkFlowStatus(event.id)
      .subscribe({
        next: (response: HttpResponse<any>) => {
          this.commonService.AClicked();
          this.notificationService.success('Composition Deleted!');
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

  searchByStatusName(event) {
    this.searchValueForStatusName = event;
    this.getApprovalWorkFlowStatus();
  }

  searchByStatusCode(event) {
    this.searchValueForStatusCode = event;
    this.getApprovalWorkFlowStatus();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.getApprovalWorkFlowStatus();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.getApprovalWorkFlowStatus();
  }

  count() {
    this.approvalWorkFlowStatusService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }

  add() {
    this.dialog.open(ApprovalWorkFlowStatusCreateEditComponent, {
      width: '600px',
      height: '400px',
      data: {
        operation: 'Create',
      },
    });
  }

  edit(collection) {
    this.dialog.open(ApprovalWorkFlowStatusCreateEditComponent, {
      width: '600px',
      height: '400px',
      data: {
        statusId: collection.id,
        statusName: collection.statusName,
        code: collection.code,
        operation: 'Update',
      },
    });
  }
}

@Component({
  templateUrl: './approval-work-flow-status-create-edit-component.html',
})
export class ApprovalWorkFlowStatusCreateEditComponent implements OnInit {
  approvalWorkFlowStatusGroup: FormGroup;
  operation: string;
  typeId: number;
  approvalWorkFlowForCollection: ApprovalWorkFlowFor[] = [];
  approvalWorkFlowStatusCollection: ApprovalWorkFlowStatus[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private notificationService: NotificationService,
    private approvalWorkFlowForService: ApprovalWorkFlowForService,
    private approvalWorkFlowStatusService: ApprovalWorkFlowStatusService,
    public dialogRef: MatDialogRef<ApprovalWorkFlowStatusCreateEditComponent>,
    private compositionService: ApprovalWorkFlowStatusService,
    public commonService: CommonService,
    private formBuilder: FormBuilder
  ) {
    this.operation = data.operation;
    this.approvalWorkFlowStatusGroup = this.formBuilder.group({
      statusName: [data?.statusName, Validators.required],
      code: [
        { value: data?.code, disabled: this.operation == 'Update' },
        Validators.required,
      ],
    });
  }

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.findAllApprovalWorkFlowFor();
  }

  findAllApprovalWorkFlowFor() {
    this.approvalWorkFlowForService.findAll().subscribe({
      next: (response: any) => {
        this.approvalWorkFlowForCollection = response;
      },
    });
  }

  findApprovalFlowStatusByForm(event) {
    this.approvalWorkFlowStatusService
      .findApprovalFlowStatusByForm(event)
      .subscribe({
        next: (response: any) => {
          this.approvalWorkFlowStatusCollection = response;
        },
      });
  }

  submit(): void {
    if (this.operation === 'Update') {
      this.approvalWorkFlowStatusService
        .updateApprovalWorkFlowStatus(
          this.data?.statusId,
          this.approvalWorkFlowStatusGroup.getRawValue()
        )
        .subscribe({
          next: (response: HttpResponse<ApprovalWorkFlowStatus>) => {
            this.notificationService.success('Status Updated!');
            this.dialogRef.close();
            this.commonService.AClicked();
          },
          error: (errorResponse: HttpErrorResponse) => {
            const error = errorResponse.error.message;
            this.notificationService.error(error);
          },
        });
    } else {
      this.approvalWorkFlowStatusService
        .createApprovalWorkFlowStatus(
          this.approvalWorkFlowStatusGroup.getRawValue()
        )
        .subscribe({
          next: (response: HttpResponse<ApprovalWorkFlowStatus>) => {
            this.notificationService.success('Status Created');
            this.dialogRef.close();
            this.commonService.AClicked();
          },
          error: (errorResponse: HttpErrorResponse) => {
            const error = errorResponse.error.message;
            this.notificationService.error(error);
          },
        });
    }
  }
}
