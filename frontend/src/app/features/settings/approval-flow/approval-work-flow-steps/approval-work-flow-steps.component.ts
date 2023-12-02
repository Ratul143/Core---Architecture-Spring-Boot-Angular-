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
import { ApprovalWorkFlowSteps } from './approval-work-flow-steps';
import { ApprovalWorkFlowStepsService } from './approval-work-flow-steps.service';
import { ApprovalWorkFlowService } from '../approval-work-flow/approval-work-flow.service';
import { ApprovalWorkFlow } from '../approval-work-flow/approval-work-flow';
import { CommonService } from 'src/app/core/services/common.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './approval-work-flow-steps.component.html',
  styleUrls: ['./approval-work-flow-steps.component.css'],
})
export class ApprovalWorkFlowStepsComponent implements OnInit {
  compositionGroup: FormGroup;
  approvalWorkFlowStepsCollection: ApprovalWorkFlowSteps[];
  update: boolean;
  userId: number;
  searchValue: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;
  searchValueForFormName: string = '';
  searchValueForStepsFrom: string = '';
  searchValueForStepsTo: string = '';

  constructor(
    private approvalWorkFlowStepsService: ApprovalWorkFlowStepsService,
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
      this.getApprovalWorkFlowStepsList();
    });
    this.getApprovalWorkFlowStepsList();
  }

  permission(type: string): boolean {
    return this.authService.permission(
      environment.approvalWorkFlowSteps.substring(1),
      type
    );
  }

  getApprovalWorkFlowStepsList() {
    this.approvalWorkFlowStepsService
      .getApprovalFlowStepsList(
        this.searchValueForFormName,
        this.searchValueForStepsFrom,
        this.searchValueForStepsTo,
        this.pageOffset,
        this.itemsPerPage
      )
      .subscribe({
        next: (response: []) => {
          this.approvalWorkFlowStepsCollection = response;
          console.log('here: ', response);
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
    this.approvalWorkFlowStepsService
      .deleteApprovalWorkFlowSteps(event.id)
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

  searchByFormName(event) {
    this.searchValueForFormName = event;
    this.getApprovalWorkFlowStepsList();
  }

  searchByStepsFrom(event) {
    this.searchValueForStepsFrom = event;
    this.getApprovalWorkFlowStepsList();
  }

  searchByStepsTo(event) {
    this.searchValueForStepsTo = event;
    this.getApprovalWorkFlowStepsList();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.getApprovalWorkFlowStepsList();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.getApprovalWorkFlowStepsList();
  }

  count() {
    this.approvalWorkFlowStepsService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }

  add() {
    this.dialog.open(ApprovalWorkFlowStepsCreateEditComponent, {
      width: '700px',
      height: '400px',
      data: {
        formId: '',
        steps: [
          {
            currentWorkFlowId: '',
            nextWorkFlowId: '',
            isFinal: null,
          },
        ],
        operation: 'Create',
      },
    });
  }

  edit(collection) {
    this.dialog.open(ApprovalWorkFlowStepsCreateEditComponent, {
      width: '400px',
      height: '400px',
      data: {
        type: collection.id,
        compositions: collection.compositions,
        operation: 'Update',
      },
    });
  }
}

@Component({
  templateUrl: './approval-work-flow-steps-create-edit.component.html',
})
export class ApprovalWorkFlowStepsCreateEditComponent implements OnInit {
  approvalWorkFlowStepsGroup: any;
  operation: string;
  typeId: number;
  approvalWorkFlowForCollection: ApprovalWorkFlowFor[] = [];
  approvalWorkFlowCollection: ApprovalWorkFlow[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private notificationService: NotificationService,
    private approvalWorkFlowForService: ApprovalWorkFlowForService,
    private approvalWorkFlowStepsService: ApprovalWorkFlowStepsService,
    public dialogRef: MatDialogRef<ApprovalWorkFlowStepsCreateEditComponent>,
    private compositionService: ApprovalWorkFlowStepsService,
    private approvalWorkFlowService: ApprovalWorkFlowService,
    private commonService: CommonService
  ) {
    this.approvalWorkFlowStepsGroup = {
      formId: data.formId,
      steps: data.steps,
      operation: data.operation,
    };
    this.operation = data.operation;
    this.typeId = data.type;
  }

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.findAllApprovalWorkFlowFor();
  }

  addRow() {
    const typeObj = {
      currentWorkFlowId: '',
      nextWorkFlowId: '',
      isFinal: null,
    };
    this.approvalWorkFlowStepsGroup.steps.push(typeObj);
  }

  deleteRow(item) {
    const index: number = this.approvalWorkFlowStepsGroup.steps.indexOf(item);
    if (index !== -1) {
      this.approvalWorkFlowStepsGroup.steps.splice(index, 1);
    }
  }

  findAllApprovalWorkFlowFor() {
    this.approvalWorkFlowForService.findAll().subscribe({
      next: (response: any) => {
        this.approvalWorkFlowForCollection = response;
      },
    });
  }

  getApprovalFlowByForm(event) {
    this.approvalWorkFlowService.findApprovalFlowStatusByForm(event).subscribe({
      next: (response: any) => {
        this.approvalWorkFlowCollection = response;
      },
    });
  }

  submit(): void {
    this.approvalWorkFlowStepsService
      .createApprovalWorkFlowStepsService(this.approvalWorkFlowStepsGroup)
      .subscribe({
        next: (response: HttpResponse<ApprovalWorkFlowSteps>) => {
          this.notificationService.success('Approval Work Flow Steps Created!');
          this.dialogRef.close();
          this.commonService.AClicked();
        },
        error: (errorResponse: HttpErrorResponse) => {
          const error = errorResponse.error.message;
          this.notificationService.error(error);
        },
      });

    // if (this.operation === 'Update') {
    //   this.compositionService.updateComposition(this.typeId, this.compositionGroup)
    //     .subscribe({
    //       next: (response: HttpResponse<Composition>) => {
    //         this.notificationService.success("Composition Updated!");
    //         this.dialogRef.close();
    //         this.commonService.AClicked();
    //         this.resetForm();
    //       },
    //       error: (errorResponse: HttpErrorResponse) => {
    //         const validationErrors = errorResponse.error.validationErrors;
    //         if (validationErrors != null) {
    //           Object.keys(validationErrors).forEach(key => {
    //             this.notificationService.error(key);
    //           });
    //         }
    //       }
    //     });
    // } else {
    //   this.compositionService.createComposition(this.compositionGroup)
    //     .subscribe({
    //       next: (response: HttpResponse<Composition>) => {
    //         this.notificationService.success("Composition Created!");
    //         this.dialogRef.close();
    //         this.commonService.AClicked();
    //         this.resetForm();
    //       },
    //       error: (errorResponse: HttpErrorResponse) => {
    //         const validationErrors = errorResponse.error.validationErrors;
    //         if (validationErrors != null) {
    //           Object.keys(validationErrors).forEach(key => {
    //             this.notificationService.error(key);
    //           });
    //         } else {
    //           const validationErrorMessage = errorResponse.error.message;
    //           this.notificationService.error(validationErrorMessage);
    //         }
    //       }
    //     });
    // }
  }

  //
  // resetForm() {
  //   this.compositionGroup = {
  //     type: '',
  //     compositions: [],
  //   };
  // }
}
