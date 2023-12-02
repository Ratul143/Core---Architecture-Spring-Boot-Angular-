import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'currency',
})
export class CurrencyPipe implements PipeTransform {
  transform(
    value: number | string,
    currencySymbol: string = 'BDT',
    decimalSeparator: string = '.',
    thousandsSeparator: string = ','
  ): string {
    if (!value) {
      return '';
    }

    const [integer, decimal] = (value as string).split(decimalSeparator);

    const integerFormatted = integer.replace(
      /\B(?=(\d{3})+(?!\d))/g,
      thousandsSeparator
    );

    if (!decimal) {
      return `${currencySymbol}${integerFormatted}`;
    }

    return `${currencySymbol}${integerFormatted}${decimalSeparator}${decimal}`;
  }
}
