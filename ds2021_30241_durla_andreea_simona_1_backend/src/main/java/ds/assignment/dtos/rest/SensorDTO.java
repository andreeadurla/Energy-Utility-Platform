package ds.assignment.dtos.rest;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SensorDTO {

    @NotNull
    private String description;
    private float maxValue;
    private UUID deviceId;
}
