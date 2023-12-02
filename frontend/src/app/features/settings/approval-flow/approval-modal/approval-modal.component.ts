import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ApprovalWorkFlowStepsService } from '../approval-work-flow-steps/approval-work-flow-steps.service';
import { ApprovalWorkFlowSteps } from '../approval-work-flow-steps/approval-work-flow-steps';
import { NotificationService } from 'src/app/core/services/notification.service';
import { CommonService } from 'src/app/core/services/common.service';
import { ApprovalWorkFlowStatus } from '../approval-work-flow-status/approval-work-flow-status';
import { FormsEnum } from 'src/app/core/classes/forms';

@Component({
  selector: 'app-approval-modal',
  templateUrl: './approval-modal.component.html',
  styleUrls: ['./approval-modal.component.css'],
})
export class ApprovalModalComponent implements OnInit {
  sendForNextApprovalGroup: FormGroup;
  sendForPreviousApprovalGroup: FormGroup;
  nextStatusName: string;
  previousStatusName: string;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private formBuilder: FormBuilder,
    private notificationService: NotificationService,
    private approvalWorkFlowStepService: ApprovalWorkFlowStepsService,
    public dialogRef: MatDialogRef<ApprovalModalComponent>,
    private commonService: CommonService
  ) {
    this.sendForNextApprovalGroup = this.formBuilder.group({
      formName: data?.formName,
      formCode: data?.formCode,
      nextStatusCode: null,
      remarks: null,
      uniqueKey: data?.data?.uniqueKey,
      nextWorkFlowStatusId: null,
    });

    this.sendForPreviousApprovalGroup = this.formBuilder.group({
      formName: data?.formName,
      formCode: data?.formCode,
      previousStatusCode: null,
      remarks: null,
      uniqueKey: data?.data?.uniqueKey,
      previousFlowStatusId: null,
      isBacked: null,
    });
  }

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.getNextSteps();
    this.getPreviousSteps();

    this.sendForNextApprovalGroup
      .get('remarks')
      .valueChanges.subscribe((value) => {
        this.sendForPreviousApprovalGroup.patchValue({
          remarks: value,
        });
      });
  }

  getNextSteps() {
    let statusId;
    if (this.data?.formCode === FormsEnum.EXAMPLE_FORM) {
      statusId = this.data?.data?.approvalWorkFlowStatus?.id;
    } else {
      statusId = this.data?.data?.statusId;
    }
    this.approvalWorkFlowStepService
      .getNextStep(this.data?.formCode, statusId)
      .subscribe({
        next: (response: ApprovalWorkFlowSteps) => {
          this.nextStatusName = response?.nextWorkFlowStatus?.statusName;
          this.sendForNextApprovalGroup.patchValue({
            nextStatusCode: response?.nextWorkFlowStatus?.code,
            remarks: null,
            nextWorkFlowStatusId: response?.nextWorkFlowStatus?.id,
          });
        },
        error: (errorResponse: HttpErrorResponse) => {
          const error = errorResponse.error;
          this.notificationService.error(error);
        },
      });
  }

  getPreviousSteps() {
    let statusId;
    if (this.data?.formCode === FormsEnum.EXAMPLE_FORM) {
      statusId = this.data?.data?.approvalWorkFlowStatus?.id;
    } else {
      statusId = this.data?.data?.statusId;
    }
    this.approvalWorkFlowStepService
      .getPreviousStep(this.data?.formCode, statusId)
      .subscribe({
        next: (response: ApprovalWorkFlowSteps) => {
          this.previousStatusName = response?.currentWorkFlowStatus?.statusName;
          this.sendForPreviousApprovalGroup.patchValue({
            previousStatusCode: response?.currentWorkFlowStatus?.code,
            remarks: null,
            previousFlowStatusId: response?.currentWorkFlowStatus?.id,
          });
        },
        error: (errorResponse: HttpErrorResponse) => {
          const error = errorResponse.error;
          this.notificationService.error(error);
        },
      });
  }

  // proceedToNextApproval() {
  //   this.formService
  //     .approveOrDecline(this.sendForNextApprovalGroup.getRawValue())
  //     .subscribe({
  //       next: (response: HttpResponse<ApprovalWorkFlowStatus>) => {
  //         this.notificationService.success(this?.nextStatusName);
  //         this.commonService.AClicked();
  //         this.dialogRef.close();
  //       },
  //       error: (errorResponse: HttpErrorResponse) => {
  //         this.notificationService.error(errorResponse?.error?.message);
  //       },
  //     });
  // }

  // declineToPreviousApproval() {
  //   this.sendForPreviousApprovalGroup.patchValue({
  //     isBacked: true,
  //   });
  //   this.formService
  //     .approveOrDecline(this.sendForPreviousApprovalGroup.value)
  //     .subscribe({
  //       next: (response: HttpResponse<ApprovalWorkFlowStatus>) => {
  //         this.notificationService.warning('Declined!');
  //         this.commonService.AClicked();
  //         this.dialogRef.close();
  //       },
  //       error: (errorResponse: HttpErrorResponse) => {
  //         this.notificationService.error(errorResponse?.error?.message);
  //       },
  //     });
  // }
}
