import { NgModule } from '@angular/core';

import { VehicleTypePipe } from './vehicle-type.pipe';

@NgModule({
  exports: [ VehicleTypePipe],
  declarations: [VehicleTypePipe]
})
export class VehicleTypeModule {}
