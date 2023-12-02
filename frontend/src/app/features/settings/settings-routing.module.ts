import { SettingsComponent } from './settings.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApprovalWorkFlowForComponent } from './approval-flow/approval-work-flow-for/approval-work-flow-for.component';
import { ApprovalWorkFlowStatusComponent } from './approval-flow/approval-work-flow-status/approval-work-flow-status.component';
import { ApprovalWorkFlowStepsComponent } from './approval-flow/approval-work-flow-steps/approval-work-flow-steps.component';
import { ApprovalWorkFlowComponent } from './approval-flow/approval-work-flow/approval-work-flow.component';
import { UserFormApprovalFlowComponent } from './approval-flow/user-form-approval-flow/user-form-approval-flow.component';
import { RecentVisitorsComponent } from './recent-visitors/recent-visitors.component';

const routes: Routes = [
  {
    path: '',
    component: SettingsComponent,
    children: [
      {
        path: 'approval-work-flow-form',
        component: ApprovalWorkFlowForComponent,
      },
      {
        path: 'approval-work-flow-status',
        component: ApprovalWorkFlowStatusComponent,
      },
      {
        path: 'approval-work-flow',
        component: ApprovalWorkFlowComponent,
      },
      {
        path: 'approval-work-flow-steps',
        component: ApprovalWorkFlowStepsComponent,
      },
      {
        path: 'user-form-approval-flow',
        component: UserFormApprovalFlowComponent,
      },
      {
        path: 'visitors',
        component: RecentVisitorsComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SettingsRoutingModule {}
