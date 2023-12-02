import { NgModule } from '@angular/core';
import { DateFormat } from './date-format.pipe';
import { CurrencyPipe } from './currency.pipe';

@NgModule({
  imports: [],
  declarations: [CurrencyPipe, DateFormat],
  exports: [CurrencyPipe, DateFormat],
})
export class AppPipesModule {}
