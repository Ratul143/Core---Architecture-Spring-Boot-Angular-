import { _Component } from '../component/component';
import { SubModule } from '../sub-module/sub-module';

export class Module {
  id: number;
  uniqueKey: string;
  moduleName: string;
  subModules: SubModule[];
  _components: _Component[];
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
