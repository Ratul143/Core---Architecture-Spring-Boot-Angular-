import { User } from 'src/app/features/user-management/user/user';
import { ApprovalWorkFlowFor } from '../approval-work-flow-for/approval-work-flow-for';
import { ApprovalWorkFlowStatus } from '../approval-work-flow-status/approval-work-flow-status';

export class ApprovalWorkFlowSteps {
  id: number;
  approvalWorkFlowFor: ApprovalWorkFlowFor;
  currentWorkFlowStatus: ApprovalWorkFlowStatus;
  nextWorkFlowStatus: ApprovalWorkFlowStatus;
  isFinalLevel: boolean;
  statusName: string;
  statusCode: string;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  createdBy: User;
  updatedBy: User;
  deletedBy: User;
}
