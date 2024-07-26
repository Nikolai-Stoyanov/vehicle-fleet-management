import {Status} from "../../shared/shared";

export interface CarRecordList {
  id: number;
  registrationNumber: string;
  brand: string;
  model: string;
  owner: string;
  status: Status;
}

export interface CarRecord {
  id: number;
  registrationNumber: string;
  category: DrivingCategoryType;
  firstRegistration: string | Date;
  vehicleType: VehicleType;
  description: string;
  brand: string;
  model: string;
  seatingCapacity: number;
  frameNumber: string;
  engineNumber: string;
  horsePower: number;
  enginePower: number;
  engineVolume: number;
  primaryColor: string;
  additionalColor: string;
  loadCapacity: number;
  ownerName:Owner;
  department: string;
  headquarters:Headquarter
  stay: string;
  totalMileage: number;
  developmentFromMileage: number;
  developmentToMileage: number;
  inspectionDateTo: string | Date;
  vignetteDateTo: string | Date;
  insuranceDateTo: string | Date;
  status:boolean
  fuels:Fuel[];
  createdBy: string;
  createdDate: string | Date;
  modifyBy: string;
  modifyDate: string | Date;
}

export interface Fuel {
  id: number;
  type:Fuels;
  tankVolume:number;
  distanceLimit:number;
  moneyLimit:number;
}



export enum Fuels{
  DIESEL='Disel', LPG='LPG', GASOLINE='Gasoline', METHANOL='Methanol',
}

export enum Headquarter{
HEADQUARTER1='Headquarter 1',HEADQUARTER2='Headquarter 2',HEADQUARTER3='Headquarter 3',HEADQUARTER4='Headquarter 4',HEADQUARTER5='Headquarter 5'
}

export enum Owner{
FIRSTOWNER='First owner',SECONDOWNER='Second owner',THIRDOWNER='Third owner',FOURTHOWNER='Fourth owner'
}

export enum VehicleType{
  CAR='Car', TRUCK='Truck'
}

export enum DrivingCategoryType{
  AM='AM', A1='A1', A2='A2', A='A', B1='B1', B='B', C1='C1', C='C', D1='D1', D='D', BE='BE', C1E='C1E', D1E='D1E', DE='DE', Ttm='Ttm', Tkt='Tkt'
}
