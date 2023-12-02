export class Role {
  id: number;
  uniqueKey: string;
  role: string;
  authorities: string[];
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  createdBy: number;
  updatedBy: number;
  deletedBy: number;
}
