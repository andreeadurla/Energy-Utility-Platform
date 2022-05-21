package ds.assignment.dtos.rest;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
public class EnergyConsumptionDTO {

    @NotNull
    private long timestamp;

    @NotNull
    private float energyConsumption;

    @NotNull
    private UUID sensorId;
}
