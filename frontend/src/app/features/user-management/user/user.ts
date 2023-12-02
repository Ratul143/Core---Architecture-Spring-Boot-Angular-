import { Role } from '../role/role';

export class User {
  public id: number;
  public uniqueKey: string;
  public reportingTo: number;
  public userId: string;
  public firstName: string;
  public lastName: string;
  public username: string;
  public email: string;
  public profileImage: File;
  public profileImageUrl: string;
  public joiningDate: Date;
  public lastJoiningDateDisplay: Date;
  public role: Role;
  public active: boolean;
  public workplace: number;
  public notLocked: boolean;
  public createdAt: Date;
  public updatedAt: Date;
  public deletedAt: Date;
  public createdBy: number;
  public updatedBy: number;
  public deletedBy: number;
}
