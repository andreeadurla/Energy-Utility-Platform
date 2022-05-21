package ds.assignment.dtos.rpc;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeviceDTO {

    private UUID id;
    private String description;
}
