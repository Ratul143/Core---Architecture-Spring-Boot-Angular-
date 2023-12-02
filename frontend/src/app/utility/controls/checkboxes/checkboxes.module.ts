import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CheckboxesComponent } from './checkboxes.component';
import { SharedModule } from 'src/app/shared/shared-module';

@NgModule({
  declarations: [CheckboxesComponent],
  imports: [CommonModule, SharedModule],
  exports: [CheckboxesComponent],
})
export class CheckboxesModule {}
