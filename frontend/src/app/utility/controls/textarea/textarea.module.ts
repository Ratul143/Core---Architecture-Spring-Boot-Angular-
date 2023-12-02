import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TextAreaComponent } from './textarea.component';
import { SharedModule } from 'src/app/shared/shared-module';

@NgModule({
  declarations: [TextAreaComponent],
  imports: [CommonModule, SharedModule],
  exports: [TextAreaComponent],
})
export class TextAreaModule {}
