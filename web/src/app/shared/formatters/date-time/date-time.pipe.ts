import { Pipe, PipeTransform } from '@angular/core';

import {format} from 'date-fns';

@Pipe({ name: 'vfmDateTime' })
export class DateTimePipe implements PipeTransform {
  public transform(value: string): string|null {
    if (value == null || value === '' || value !== value) return null;

    if (value.toString().indexOf('[') !== -1) {
      const date = value.split('[')[0];
      return format(new Date(date),'dd.MM.yyyy HH:mm:ss');
    }
    if (value.toString().indexOf('-') > 0) {
      return format(new Date(value),'dd.MM.yyyy HH:mm:ss');
    }
    return format(new Date(value),'dd.MM.yyyy HH:mm:ss');
  }
}
