package ds.assignment.rpc.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProgramSelectionDTO {

    private String startTime;
    private String endTime;
    private float[] averagedEnergyConsumption;
    private float[] estimatedConsumption;
}
