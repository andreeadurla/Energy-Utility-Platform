package ds.assignment.dtos.rest;

import ds.assignment.dtos.rest.validators.annotations.AddressConstraint;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.UUID;

@Data
public class DeviceDTO {

    @NotNull
    private String description;

    @NotNull
    @AddressConstraint
    private String address;

    @NotNull
    @Min(value = 0)
    private float maxEnergyConsumption;

    @NotNull
    @Min(value = 0)
    private float avgEnergyConsumption;

    private UUID clientId;
}
