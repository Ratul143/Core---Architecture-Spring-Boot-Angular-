import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableExceptPagesComponent } from './table-except-pages.component';
import { SharedModule } from 'src/app/shared/shared-module';
import { MaterialModule } from 'src/app/shared/material.module';

@NgModule({
  declarations: [TableExceptPagesComponent],
  imports: [CommonModule, SharedModule, MaterialModule],
  exports: [TableExceptPagesComponent],
})
export class TableExceptPagesModule {}
