import { NgModule } from '@angular/core';

import { ArrStringPipe } from './arr-string.pipe';

@NgModule({
  exports: [ ArrStringPipe],
  declarations: [ArrStringPipe]
})
export class ArrStringModule {}
