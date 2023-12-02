import {Module} from "../module/module";
import {SubModule} from "../sub-module/sub-module";
import {User} from "../../user-management/user/user";
import {_Component} from "../component/component";
import {Role} from "../../user-management/role/role";

export class UserBasedPermission {
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
