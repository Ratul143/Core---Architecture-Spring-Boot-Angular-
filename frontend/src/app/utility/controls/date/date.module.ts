import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateComponent } from './date.component';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';


@NgModule({
    declarations: [DateComponent],
    imports: [
        CommonModule,
        MatDatepickerModule,
        MatInputModule,
        MatIconModule,
        MatFormFieldModule
    ],
    exports: [
        DateComponent
    ]
})
export class DateModule { }
