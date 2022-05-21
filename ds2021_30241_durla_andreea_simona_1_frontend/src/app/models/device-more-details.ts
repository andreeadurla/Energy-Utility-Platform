import { SensorDetails } from "./sensor-details";

export interface DeviceMoreDetails {
    id: string;
    description: string;
    address: string;
    maxEnergyConsumption: number;
    avgEnergyConsumption: number;
    sensorDetails: SensorDetails;
}
