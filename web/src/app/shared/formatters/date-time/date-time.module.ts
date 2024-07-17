import { NgModule } from '@angular/core';

import { DateTimePipe } from './date-time.pipe';

@NgModule({
  exports: [ DateTimePipe],
  declarations: [DateTimePipe]
})
export class DateTimeModule {}
