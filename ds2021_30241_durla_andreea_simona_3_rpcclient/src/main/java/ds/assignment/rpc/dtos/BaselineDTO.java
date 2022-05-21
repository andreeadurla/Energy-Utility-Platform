package ds.assignment.rpc.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaselineDTO {

    private String startDate;
    private String endDate;
    private float[] averagedEnergyConsumption;
}
