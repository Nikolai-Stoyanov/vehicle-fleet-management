import { InjectionToken } from '@angular/core';

export const ENVIRONMENT = new InjectionToken<any>('ENVIRONMENT');

export interface ServiceUrl {
  baseUrl: string;
}

export interface EnvironmentInfo {
  name: string;
}

export interface RuntimeConfiguration {
  backend: ServiceUrl;
  environment: EnvironmentInfo;
  appName: string;
  appVersion: string;
  buildId: string;
  buildDate: string;
  debugIsEnabled?: boolean;
}

export enum Status {
  ACTIVE,DISABLED
}

export enum EditFormTypes {
  VIGNETTES='Vignettes'
  ,INSPECTIONS='Inspections'
  ,INSURANCES='Insurances'
  ,RESPONSIBLE='Responsible'
  ,DRIVERS='Drivers'
  ,REGISTRATIONNUMBER='Registration numbers'
  ,FUELCARD='Fuel cards'
}
