import { Role } from './../../features/user-management/role/role';

export class AccsAuthUser {
  public id: number;
  public username: string;
  public fullName: string;
  public email: string;
  public token: string;
  public type: string;
  public role: Role;
}
