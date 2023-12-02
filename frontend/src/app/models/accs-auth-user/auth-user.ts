import { Role } from 'src/app/features/user-management/role/role';

export class AuthUser {
  public id: number;
  public username: string;
  public email: string;
  public password: string;
  public fullName: string;
  public cellNo: string;
  public employeeId: number;
  public signature: string;
  public countryName: string;
  public isDeleted: boolean;
  public enabled: boolean;
  public createdAt: Date;
  public createdBy: string;
  public modifiedAt: Date;
  public modifiedBy: string;
  public deletedById: number;
  public deletedAt: Date;
  public accountExpired: boolean;
  public accountLocked: boolean;
  public passwordExpired: boolean;
  public role: Role;
}
