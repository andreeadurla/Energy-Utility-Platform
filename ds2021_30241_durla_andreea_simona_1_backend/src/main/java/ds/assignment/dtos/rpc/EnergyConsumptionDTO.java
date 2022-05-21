package ds.assignment.dtos.rpc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnergyConsumptionDTO {

    private String date;
    private float[] energyConsumption;
}
