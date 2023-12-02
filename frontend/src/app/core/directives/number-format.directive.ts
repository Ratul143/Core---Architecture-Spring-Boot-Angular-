import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appNumberFormat]',
})
export class NumberFormatDirective {
  constructor(private el: ElementRef) {}

  @HostListener('input', ['$event'])
  onInput(event: Event) {
    const input = event.target as HTMLInputElement;
    const value = input.value.replace(/[^0-9.]/g, ''); // Remove non-numeric characters
    input.value = parseFloat(value).toFixed(2); // Format to 2 decimal places
  }
}
