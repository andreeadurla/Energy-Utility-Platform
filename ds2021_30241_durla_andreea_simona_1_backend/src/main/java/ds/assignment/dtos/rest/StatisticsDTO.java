package ds.assignment.dtos.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsDTO {

    private float statisticsOnHours[] = new float[24];
}
