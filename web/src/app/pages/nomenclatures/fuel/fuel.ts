export interface FuelProviderType {
  id: number;
  name: string;
  description: string;
  fuelOptions?: any[];
  status: boolean;
  fuelList?: any[];
}

export interface FuelType {
  id: number;
  name: string;
  description: string;
  status: boolean;
}

export interface FuelOption {
  id: number;
  name: string;
}
