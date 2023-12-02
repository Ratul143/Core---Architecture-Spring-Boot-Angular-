import {
  CUSTOM_ELEMENTS_SCHEMA,
  NgModule,
  NO_ERRORS_SCHEMA,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImageUploadComponent } from './image-upload.component';
import { SharedModule } from 'src/app/shared/shared-module';
import { MaterialModule } from 'src/app/shared/material.module';

@NgModule({
  declarations: [ImageUploadComponent],
  imports: [CommonModule, SharedModule, MaterialModule],
  exports: [ImageUploadComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
})
export class ImageUploadModule {}
