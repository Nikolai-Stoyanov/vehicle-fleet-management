export interface DeclarationList {
  id: number;
  period: string | Date;
  responsible: string;
  registrationNumber: string;
  date: string | Date;
  driver: string;
}

export interface Declaration {
  id: number;
  period: string | Date;
  date: string | Date;
  lastMileage: number;
  newMileage: number;
  mileage:number;
  responsible: string;
  registrationNumber: string;
  fuelType: string;
  carId: number;
  fuelKind: any;
  fuelSupplier: any;
  fuelAmount: number;
  fuelPrice: number;
  createdBy: string;
  createdAt: string;
  updatedBy: string;
  updatedAt: string;
}
