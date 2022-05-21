package ds.assignment.rpc.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class DeviceDTO {

    private UUID id;
    private String description;

    @Override
    public String toString() {
        return description;
    }
}
