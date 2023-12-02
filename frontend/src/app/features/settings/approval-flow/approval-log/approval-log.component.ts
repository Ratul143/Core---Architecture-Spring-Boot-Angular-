import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';
import { ApprovalLogService } from './approval-log.service';
import { ApprovalLog } from './approval-log';
import { NotificationService } from 'src/app/core/services/notification.service';
import { CommonService } from 'src/app/core/services/common.service';

@Component({
  selector: 'app-approval-log',
  templateUrl: './approval-log.component.html',
  styleUrls: ['./approval-log.component.css'],
})
export class ApprovalLogComponent implements OnInit {
  approvalLogCollection: ApprovalLog[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<ApprovalLogComponent>,
    private notificationService: NotificationService,
    private approvalLogService: ApprovalLogService,
    public commonService: CommonService
  ) {}

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.findFormLogs(this?.data?.formUniqueId);
  }

  findFormLogs(formUniqueId: string) {
    this.approvalLogService.findLogsByUniqueId(formUniqueId).subscribe({
      next: (response: ApprovalLog[]) => {
        this.approvalLogCollection = response;
      },
      error: (errorResponse: HttpErrorResponse) => {
        const error = errorResponse.error;
        this.notificationService.error(error);
      },
    });
  }
}
