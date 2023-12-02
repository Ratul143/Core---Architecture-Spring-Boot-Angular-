import {Module} from "../module/module";

export class SubModule {
  id: number;
  uniqueKey: string;
  subModuleName: string;
  module: Module;
  icon: string;
  isActive: boolean;
  sortOrder: string;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  createdBy: number;
  updatedBy: number;
  deletedBy: number;
}
