package ds.assignment.dtos.rpc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaselineDTO {

    private String startDate;
    private String endDate;
    private float[] averagedEnergyConsumption;
}
