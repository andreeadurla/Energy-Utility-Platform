package ds.assignment.dtos.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class MonitoredValueDetailsDTO {

    private long timestamp;
    private float energyConsumption;
    private UUID sensorId;

    public MonitoredValueDetailsDTO(long timestamp, float energyConsumption) {
        this.timestamp = timestamp;
        this.energyConsumption = energyConsumption;
    }
}
