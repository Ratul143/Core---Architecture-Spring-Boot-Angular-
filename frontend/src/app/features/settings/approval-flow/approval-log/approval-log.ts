import { ApprovalWorkFlowStatus } from '../approval-work-flow-status/approval-work-flow-status';
import { ApprovalWorkFlowFor } from '../approval-work-flow-for/approval-work-flow-for';
import { User } from 'src/app/features/user-management/user/user';

export class ApprovalLog {
  id: number;
  approvalWorkFlowFor: ApprovalWorkFlowFor;
  approvalWorkFlowStatus: ApprovalWorkFlowStatus;
  remarks: string;
  isBacked: boolean;
  processedAt: Date;
  processBy: User;
}
