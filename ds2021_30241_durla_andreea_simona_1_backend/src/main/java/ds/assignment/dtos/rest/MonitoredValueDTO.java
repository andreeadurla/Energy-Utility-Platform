package ds.assignment.dtos.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MonitoredValueDTO {

    @NotNull
    private long timestamp;

    @NotNull
    private float measurementValue;

    @NotNull
    private UUID sensorId;
}
