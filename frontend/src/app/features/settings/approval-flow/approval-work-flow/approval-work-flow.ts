import { ApprovalWorkFlowStatus } from '../approval-work-flow-status/approval-work-flow-status';
import { ApprovalWorkFlowFor } from '../approval-work-flow-for/approval-work-flow-for';
import { User } from 'src/app/features/user-management/user/user';

export class ApprovalWorkFlow {
  id: number;
  statusLevel: string;
  isFinalLevel: boolean;
  isActive: boolean;
  approvalWorkFlowFor: ApprovalWorkFlowFor;
  approvalWorkFlowStatus: ApprovalWorkFlowStatus;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  createdBy: User;
  updatedBy: User;
  deletedBy: User;
}
