import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'vfmArrToStringPipe'})
export class ArrStringPipe implements PipeTransform {
  public transform(value: any[]): string | null {
    if (value == null || value.length === 0 || value !== value) return null;
    let data = '';
    if (typeof value[0] === "object") {
      value.forEach(item => {
        data += item.name + ', ';
      })
      return data;
    }
    if (typeof value[0] === "string") {
      value.forEach(item => {
        data += item + ', ';
      })
      return data;
    }
    return data;
  }
}
