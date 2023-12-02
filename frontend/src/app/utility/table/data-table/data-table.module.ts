import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataTableComponent } from './data-table.component';
import { ActionButtonsComponent } from '../action-buttons/action-buttons.component';
import { EllipsisButtonsComponent } from '../ellipsis-buttons/ellipsis-buttons.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared-module';

@NgModule({
  declarations: [
    DataTableComponent,
    ActionButtonsComponent,
    EllipsisButtonsComponent,
  ],
  imports: [CommonModule, SharedModule, MaterialModule],
  exports: [DataTableComponent],
})
export class DataTableModule {}
