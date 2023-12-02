import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RadiosComponent } from './radios.component';
import { SharedModule } from 'src/app/shared/shared-module';

@NgModule({
  declarations: [RadiosComponent],
  imports: [CommonModule, SharedModule],
  exports: [RadiosComponent],
})
export class RadiosModule {}
