import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormDetailComponent } from './form-detail.component';
import { SharedModule } from 'src/app/shared/shared-module';
import { ButtonModule } from '../../buttons/button/button.module';
import { MaterialModule } from 'src/app/shared/material.module';

@NgModule({
  declarations: [FormDetailComponent],
  imports: [CommonModule, SharedModule, ButtonModule, MaterialModule],
  exports: [FormDetailComponent],
})
export class FormDetailModule {}
