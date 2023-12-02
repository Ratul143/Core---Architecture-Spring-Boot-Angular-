import { NgModule } from '@angular/core';

import { AutofocusDirective } from './i-autofocus.directive';
import { UppercaseDirective } from './uppercase.directive';

@NgModule({
    imports: [],
    declarations: [
        AutofocusDirective,
        UppercaseDirective
    ],
    exports: [
        AutofocusDirective,
        UppercaseDirective
    ]
})
export class AppDirectivesModule { }
