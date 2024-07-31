import {Pipe, PipeTransform} from '@angular/core';
import { VehicleType} from "../../../pages/car-records/car-record";

@Pipe({name: 'vfmVehicleTypePipe'})
export class VehicleTypePipe implements PipeTransform {
  public result:any;

  public transform(value: any): string {
    if(typeof value === 'string') {
      if (value === 'CAR') {
        this.result = {id:1,name:VehicleType.CAR};
      } else if (value === 'TRUCK') {
        this.result = {id:2,name:VehicleType.TRUCK};
      }
    }else if(typeof value === 'object') {
      if (value.id === 1) {
        this.result = 'CAR';
      } else if (value.id === 2) {
        this.result = 'TRUCK';
      }
    }
    return this.result;
  }
}
