import { Pipe, PipeTransform } from '@angular/core';

import {format} from 'date-fns';

@Pipe({standalone: true, name: 'vfmDateTimeForBackend'})
export class DateTimeForBackendPipe implements PipeTransform {
  public transform(value: any): string {
    if (value == null || value === '' || value !== value) { // @ts-ignore
      return null;
    }

    return format((value),'yyyy-MM-dd');

  }
}
