package ds.assignment.dtos.rest;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AssignSensorToDeviceDTO {

    @NotNull
    private UUID deviceId;
    private UUID newSensorId;
    private UUID removedSensorId;
}
