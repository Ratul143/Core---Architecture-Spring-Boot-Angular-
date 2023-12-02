import { User } from 'src/app/features/user-management/user/user';
import { ApprovalWorkFlowFor } from '../approval-work-flow-for/approval-work-flow-for';
import { ApprovalWorkFlowStatus } from '../approval-work-flow-status/approval-work-flow-status';
import { ApprovalWorkFlow } from '../approval-work-flow/approval-work-flow';

export class UserFormApprovalFlow {
  id: number;
  approvalWorkFlowFor: ApprovalWorkFlowFor;
  user: User;
  approvalWorkFlowStatus: ApprovalWorkFlowStatus;
  approvalWorkFlow: ApprovalWorkFlow;
  isFinal: boolean;
  isActive: boolean;
  statusName: string;
  statusCode: string;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  createdBy: User;
  updatedBy: User;
  deletedBy: User;
}
