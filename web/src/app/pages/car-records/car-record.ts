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

  department: string;

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

  // @ts-ignore
  DIESEL= $localize`diesel`, LPG= $localize`LPG`, GASOLINE= $localize`gasoline`, METHANOL= $localize`methanol`,
}

export enum VehicleType{

  // @ts-ignore
  CAR=$localize`CAR`, TRUCK= $localize`TRUCK`
}

export enum DrivingCategoryType{
  AM='AM', A1='A1', A2='A2', A='A', B1='B1', B='B', C1='C1', C='C', D1='D1', D='D', BE='BE', C1E='C1E', D1E='D1E', DE='DE', Ttm='Ttm', Tkt='Tkt'
}

export const FUELOPTIONS = [
  {value: {id:1,name:Fuels.DIESEL}, label: Fuels.DIESEL},
  {value: {id:2,name:Fuels.GASOLINE}, label: Fuels.GASOLINE},
  {value: {id:3,name:Fuels.LPG}, label: Fuels.LPG},
  {value: {id:4,name:Fuels.METHANOL}, label: Fuels.METHANOL},
];

export const VEHICLEOPTIONS = [
  {value: {id:1,name:VehicleType.CAR}, label: VehicleType.CAR},
  {value: {id:2,name:VehicleType.TRUCK}, label: VehicleType.TRUCK},
];

export const CATEGORYOPTIONS = [
  {value: {id:1,name:DrivingCategoryType.A}, label: DrivingCategoryType.A},
  {value: {id:2,name:DrivingCategoryType.B}, label: DrivingCategoryType.B},
  {value: {id:3,name:DrivingCategoryType.BE}, label: DrivingCategoryType.BE},
  {value: {id:4,name:DrivingCategoryType.C}, label: DrivingCategoryType.C},
  {value: {id:5,name:DrivingCategoryType.C1E}, label: DrivingCategoryType.C1E},
  {value: {id:6,name:DrivingCategoryType.D}, label: DrivingCategoryType.D},
  {value: {id:7,name:DrivingCategoryType.D1E}, label: DrivingCategoryType.D1E},
  {value: {id:8,name:DrivingCategoryType.DE}, label: DrivingCategoryType.DE},
  {value: {id:9,name:DrivingCategoryType.Ttm}, label: DrivingCategoryType.Ttm},
  {value: {id:10,name:DrivingCategoryType.Tkt}, label: DrivingCategoryType.Tkt},
];
