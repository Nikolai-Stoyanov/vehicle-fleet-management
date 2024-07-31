import { NgModule } from '@angular/core';

import { FuelsPipe } from './fuels.pipe';

@NgModule({
  exports: [ FuelsPipe],
  declarations: [FuelsPipe]
})
export class FuelsModule {}
