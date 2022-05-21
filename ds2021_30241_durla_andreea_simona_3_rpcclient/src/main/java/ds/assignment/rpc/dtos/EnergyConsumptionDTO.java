package ds.assignment.rpc.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnergyConsumptionDTO {

    private String date;
    private float[] energyConsumption;
}
