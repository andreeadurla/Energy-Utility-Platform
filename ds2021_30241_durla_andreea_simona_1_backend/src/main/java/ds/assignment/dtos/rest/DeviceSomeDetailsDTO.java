package ds.assignment.dtos.rest;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeviceSomeDetailsDTO {

    private UUID id;
    private String description;
    private UUID sensorId;
}
