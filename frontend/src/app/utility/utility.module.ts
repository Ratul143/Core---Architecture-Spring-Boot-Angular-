import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  CheckboxesModule,
  DateModule,
  FormFieldModule,
  ImageUploadModule,
  InputModule,
  MultipleSelectModule,
  PasswordModule,
  RadiosModule,
  SelectModule,
  TextAreaModule,
} from './controls';
import { ButtonModule } from './buttons/button/button.module';
import { FilesUploadModule } from './controls/files-upload/files-upload.module';
import { DataTableModule } from './table/data-table/data-table.module';
import { SearchComponent } from './components/search/search.component';
import { FormDetailModule } from './table/form-detail/form-detail.module';
import { TableExceptPagesModule } from './table/table-except-pages/table-except-pages.module';
import { SharedModule } from '../shared/shared-module';

@NgModule({
  declarations: [SearchComponent],
  imports: [
    CommonModule,
    SharedModule,
    InputModule,
    FormFieldModule,
    PasswordModule,
    DateModule,
    CheckboxesModule,
    RadiosModule,
    ButtonModule,
    FilesUploadModule,
    TextAreaModule,
    DataTableModule,
    SelectModule,
    MultipleSelectModule,
    FormDetailModule,
    TableExceptPagesModule,
  ],
  exports: [
    SearchComponent,
    SharedModule,
    InputModule,
    FormFieldModule,
    PasswordModule,
    DateModule,
    CheckboxesModule,
    RadiosModule,
    ButtonModule,
    FilesUploadModule,
    TextAreaModule,
    DataTableModule,
    SelectModule,
    MultipleSelectModule,
    FormDetailModule,
    ImageUploadModule,
    TableExceptPagesModule,
  ],
})
export class UtilityModule {}
