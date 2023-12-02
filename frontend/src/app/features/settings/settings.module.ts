import { SettingsRoutingModule } from './settings-routing.module';
import { SharedModule } from './../../shared/shared-module';
import {
  CUSTOM_ELEMENTS_SCHEMA,
  NO_ERRORS_SCHEMA,
  NgModule,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApprovalLogComponent } from './approval-flow/approval-log/approval-log.component';
import { ApprovalModalComponent } from './approval-flow/approval-modal/approval-modal.component';
import {
  ApprovalWorkFlowForComponent,
  ApprovalWorkFlowForCreateEditComponent,
} from './approval-flow/approval-work-flow-for/approval-work-flow-for.component';
import {
  ApprovalWorkFlowStatusComponent,
  ApprovalWorkFlowStatusCreateEditComponent,
} from './approval-flow/approval-work-flow-status/approval-work-flow-status.component';
import {
  ApprovalWorkFlowStepsComponent,
  ApprovalWorkFlowStepsCreateEditComponent,
} from './approval-flow/approval-work-flow-steps/approval-work-flow-steps.component';
import {
  ApprovalWorkFlowComponent,
  ApprovalFlowCreateEditComponent,
} from './approval-flow/approval-work-flow/approval-work-flow.component';
import {
  UserFormApprovalFlowComponent,
  UserFormApprovalFlowCreateEditComponent,
} from './approval-flow/user-form-approval-flow/user-form-approval-flow.component';
import { RecentVisitorsComponent } from './recent-visitors/recent-visitors.component';
import { SettingsComponent } from './settings.component';

@NgModule({
  declarations: [
    ApprovalWorkFlowForComponent,
    ApprovalWorkFlowForCreateEditComponent,
    ApprovalWorkFlowStatusComponent,
    ApprovalWorkFlowStatusCreateEditComponent,
    ApprovalWorkFlowStepsComponent,
    ApprovalWorkFlowStepsCreateEditComponent,
    ApprovalWorkFlowComponent,
    ApprovalFlowCreateEditComponent,
    ApprovalModalComponent,
    ApprovalLogComponent,
    UserFormApprovalFlowCreateEditComponent,
    UserFormApprovalFlowComponent,
    RecentVisitorsComponent,
    SettingsComponent,
  ],
  imports: [SharedModule, CommonModule, SettingsRoutingModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
})
export class SettingsModule {}
