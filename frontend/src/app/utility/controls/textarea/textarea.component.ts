import { Component, OnInit, forwardRef, Input, Output, EventEmitter } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

@Component({
    selector: 'app-textarea',
    templateUrl: './textarea.component.html',
    styleUrls: ['./textarea.component.scss'],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => TextAreaComponent),
            multi: true
        }
    ]
})
export class TextAreaComponent implements OnInit, ControlValueAccessor {
    @Input() placeholder: string;
    @Output() changed = new EventEmitter<string>();
    @Input() rows: number;

    value: string;
    @Input() isDisabled: boolean;

    constructor() { }

    ngOnInit(): void {
    }

    private propagateChange: any = () => { };
    private propagateTouched: any = () => { };

    writeValue(value: string): void {
        this.value = value;
    }

    registerOnChange(fn: any): void {
        this.propagateChange = fn;
    }

    registerOnTouched(fn: any): void {
        this.propagateTouched = fn;
    }

    setDisabledState(isDisabled: boolean): void {
        this.isDisabled = isDisabled;
    }

    onKeyup(e: any): void {
        const target = e.target as HTMLTextAreaElement;
        this.value = target.value;
        this.propagateChange(this.value);
        this.changed.emit(this.value);
    }

    onBlur(): void {
        this.propagateTouched();
    }
}
