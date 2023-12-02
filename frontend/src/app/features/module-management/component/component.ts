import {Module} from "../module/module";
import {SubModule} from "../sub-module/sub-module";

export class _Component {
  id: number;
  uniqueKey: string;
  module: Module
  subModule: SubModule
  componentName: string;
  icon: string;
  path: string;
  sortOrder: number;
  isActive: boolean;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
  createdBy: number;
  updatedBy: number;
  deletedBy: number;
}
