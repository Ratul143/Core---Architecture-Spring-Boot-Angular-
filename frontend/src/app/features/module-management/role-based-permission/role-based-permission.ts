import {Module} from "../module/module";
import {Role} from "../../user-management/role/role";
import {SubModule} from "../sub-module/sub-module";
import {_Component} from "../component/component";

export class RoleBasedPermission {
  id: number;
  role: Role;
  module: Module;
  subModule: SubModule;
  component: _Component;
  create: boolean;
  read: boolean;
  update: boolean;
  delete: boolean;
  uniqueKey: string;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  createdBy: number;
  updatedBy: number;
  deletedBy: number;
}
