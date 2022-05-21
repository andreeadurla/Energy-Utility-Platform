export interface LimitsExceededNotification {
    timestamp: Date;
    energyConsumption: number;
    sensorId: string;
    message: string;
    read?: boolean;
}
