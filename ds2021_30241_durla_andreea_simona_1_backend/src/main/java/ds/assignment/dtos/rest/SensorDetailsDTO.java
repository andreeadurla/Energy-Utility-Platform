package ds.assignment.dtos.rest;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@Builder
public class SensorDetailsDTO extends RepresentationModel<SensorDetailsDTO> {

    private UUID id;
    private String description;
    private float maxValue;
    private boolean isAssigned;
}
