package ds.assignment.dtos.rpc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgramSelectionDTO {

    private String startTime;
    private String endTime;
    private float[] averagedEnergyConsumption;
    private float[] estimatedConsumption;
}
