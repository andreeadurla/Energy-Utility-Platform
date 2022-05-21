package ds.assignment.dtos.rest;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@Builder
public class DeviceDetailsDTO extends RepresentationModel<DeviceDetailsDTO> {

    private UUID id;
    private String description;
    private String address;
    private float maxEnergyConsumption;
    private float avgEnergyConsumption;
    private boolean isAssigned;
}