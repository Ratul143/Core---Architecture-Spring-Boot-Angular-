import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PasswordComponent } from './password.component';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';



@NgModule({
    declarations: [PasswordComponent],
    imports: [
        CommonModule,
        MatIconModule,
        MatFormFieldModule
    ],
    exports: [
        PasswordComponent
    ]
})
export class PasswordModule { }
