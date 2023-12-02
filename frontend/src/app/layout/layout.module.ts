import { MainShellComponent } from './main-shell/main-shell.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared-module';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [MainShellComponent, FooterComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    HttpClientModule,
    HttpClientJsonpModule,
  ],
})
export class LayoutModule {}
