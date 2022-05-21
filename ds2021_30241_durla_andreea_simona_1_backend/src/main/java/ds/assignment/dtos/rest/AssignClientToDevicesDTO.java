package ds.assignment.dtos.rest;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class AssignClientToDevicesDTO {

    @NotNull
    private UUID clientId;

    @NotNull
    private List<UUID> newDevicesId;

    @NotNull
    private List<UUID> removedDevicesId;
}
