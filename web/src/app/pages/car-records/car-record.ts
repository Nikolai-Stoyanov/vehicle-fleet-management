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
  DIESEL, LPG, GASOLINE, METHANOL,
}

export enum Headquarter{
HEADQUARTER1,HEADQUARTER2,HEADQUARTER3,HEADQUARTER4,HEADQUARTER5
}

export enum Owner{
FIRSTOWNER,SECONDOWNER,THIRDOWNER,FOURTHOWNER
}

export enum VehicleType{
  CAR, TRUCK
}

export enum DrivingCategoryType{
  AM, A1, A2, A, B1, B, C1, C, D1, D, BE, C1E, D1E, DE, Ttm, Tkt
}
