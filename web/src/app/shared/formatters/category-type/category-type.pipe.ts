import {Pipe, PipeTransform} from '@angular/core';
import {DrivingCategoryType} from "../../../pages/car-records/car-record";

@Pipe({name: 'vfmCategoryTypePipe'})
export class CategoryTypePipe implements PipeTransform {
  public result:any;

  public transform(value: any): string {
    if(typeof value === 'string') {
      if (value === 'A') {
        this.result = {id:1,name:DrivingCategoryType.A};
      } else if (value === 'B') {
        this.result = {id:2,name:DrivingCategoryType.B};
      }else if (value === 'BE') {
        this.result = {id:3,name:DrivingCategoryType.BE};
      }else if (value === 'C') {
        this.result = {id:4,name:DrivingCategoryType.C};
      }else if (value === 'C1E') {
        this.result = {id:5,name:DrivingCategoryType.C1E};
      }else if (value === 'D') {
        this.result = {id:6,name:DrivingCategoryType.D};
      }else if (value === 'D1E') {
        this.result = {id:7,name:DrivingCategoryType.D1E};
      }else if (value === 'DE') {
        this.result = {id:8,name:DrivingCategoryType.DE};
      }else if (value === 'Ttm') {
        this.result = {id:9,name:DrivingCategoryType.Ttm};
      }else if (value === 'Tkt') {
        this.result = {id:10,name:DrivingCategoryType.Tkt};
      }
    }else if(typeof value === 'object') {
      if (value.id === 1) {
        this.result = 'A';
      } else if (value.id === 2) {
        this.result = 'B';
      }else if (value.id === 3) {
        this.result = 'BE';
      }else if (value.id === 4) {
        this.result = 'C';
      }else if (value.id === 5) {
        this.result = 'C1E';
      }else if (value.id === 6) {
        this.result = 'D';
      }else if (value.id === 7) {
        this.result = 'D1E';
      }else if (value.id === 8) {
        this.result = 'DE';
      }else if (value.id === 9) {
        this.result = 'Ttm';
      }else if (value.id === 10) {
        this.result = 'Tkt';
      }
    }
    return this.result;
  }
}
