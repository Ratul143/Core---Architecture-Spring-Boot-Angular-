import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'customRounding',
})
export class RoundingPipe implements PipeTransform {
  transform(value: number | string): number | string {
    const numericValue = Number(value);

    if (isNaN(numericValue)) {
      return value; // Return the original value if it's not a valid number
    }

    const decimal = numericValue % 1;

    if (decimal >= 0.01 && decimal <= 0.5) {
      // Round down to the nearest integer and add 0.5
      return Math.floor(numericValue) + 0.5;
    } else if (decimal >= 0.51 && decimal <= 0.99) {
      // Round up to the nearest integer
      return Math.ceil(numericValue);
    } else {
      // No rounding needed, return the same value
      return numericValue;
    }
  }
}
