import { User } from 'src/app/features/user-management/user/user';

export class ApprovalWorkFlowFor {
  id: number;
  flowFor: string;
  code: string;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  createdBy: User;
  updatedBy: User;
  deletedBy: User;
}
