import { NgModule } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { MultipleSelectComponent } from './multiple-select.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared-module';

@NgModule({
  declarations: [MultipleSelectComponent],
  imports: [NgSelectModule, FormsModule, SharedModule, CommonModule],
  exports: [MultipleSelectComponent],
})
export class MultipleSelectModule {}
