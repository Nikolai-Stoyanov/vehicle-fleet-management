import {Pipe, PipeTransform} from '@angular/core';
import {Fuels} from "../../../pages/car-records/car-record";

@Pipe({name: 'vfmFuelPipe'})
export class FuelsPipe implements PipeTransform {
  public result:any;

  public transform(value: any): string {
    if(typeof value === 'string') {
      if (value === 'DIESEL') {
        this.result = {id:1,name:Fuels.DIESEL};
      } else if (value === 'GASOLINE') {
        this.result = {id:2,name:Fuels.GASOLINE};
      } else if (value === 'LPG') {
        this.result = {id:3,name:Fuels.LPG};
      } else if (value === 'METHANOL') {
        this.result = {id:4,name:Fuels.METHANOL};
      }
    }else if(typeof value === 'object') {
      if (value.id === 1) {
        this.result = 'DIESEL';
      } else if (value.id === 2) {
        this.result = 'GASOLINE';
      } else if (value.id === 3) {
        this.result = 'LPG';
      } else if (value.id === 4) {
        this.result = 'METHANOL';
      }
    }

    return this.result;
  }
}
