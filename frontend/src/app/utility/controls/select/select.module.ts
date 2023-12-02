import { NgModule } from '@angular/core';
import { SelectComponent } from './select.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared-module';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  declarations: [SelectComponent],
  imports: [NgSelectModule, FormsModule, SharedModule, CommonModule],
  exports: [SelectComponent],
})
export class SelectModule {}
