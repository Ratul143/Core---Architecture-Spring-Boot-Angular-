import { User } from 'src/app/features/user-management/user/user';
import { ApprovalWorkFlowFor } from '../approval-work-flow-for/approval-work-flow-for';

export class ApprovalWorkFlowStatus {
  id: number;
  approvalWorkFlowFor: ApprovalWorkFlowFor;
  approvalWorkFlowStatus: ApprovalWorkFlowStatus;
  statusName: string;
  code: string;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  createdBy: User;
  updatedBy: User;
  deletedBy: User;
}
