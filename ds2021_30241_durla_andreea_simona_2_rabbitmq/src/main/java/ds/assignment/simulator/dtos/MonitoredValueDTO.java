package ds.assignment.simulator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class MonitoredValueDTO {

    private long timestamp;
    private float measurementValue;
    private UUID sensorId;
}
