import { NgModule } from '@angular/core';
import { pageNotFoundRoutes } from './page-not-found.route';

import { PageNotFoundComponent } from './page-not-found.component';

@NgModule({
  imports: [pageNotFoundRoutes],
  declarations: [PageNotFoundComponent],
})
export class PageNotFoundModule {}
