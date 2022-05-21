package ds.assignment.dtos.rest;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeviceMoreDetailsDTO {

    private UUID id;
    private String description;
    private String address;
    private float maxEnergyConsumption;
    private float avgEnergyConsumption;
    private SensorDetailsDTO sensorDetails;
}
