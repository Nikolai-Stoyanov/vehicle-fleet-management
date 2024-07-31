import { NgModule } from '@angular/core';

import { CategoryTypePipe } from './category-type.pipe';

@NgModule({
  exports: [ CategoryTypePipe],
  declarations: [CategoryTypePipe]
})
export class CategoryTypeModule {}
