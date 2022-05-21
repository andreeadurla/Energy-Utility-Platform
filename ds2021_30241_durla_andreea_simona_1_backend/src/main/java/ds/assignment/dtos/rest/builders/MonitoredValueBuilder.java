package ds.assignment.dtos.rest.builders;

import ds.assignment.dtos.rest.MonitoredValueDTO;
import ds.assignment.dtos.rest.MonitoredValueDetailsDTO;
import ds.assignment.entities.MonitoredValue;
import ds.assignment.entities.Sensor;
import ds.assignment.utils.ApplicationUtils;

import java.sql.Timestamp;

public class MonitoredValueBuilder {

    private MonitoredValueBuilder() {}

    public static MonitoredValueDetailsDTO toMonitoredValueDetailsDTO(MonitoredValue monitoredValue) {

        return MonitoredValueDetailsDTO.builder()
                .timestamp(monitoredValue.getTimestamp().getTime())
                .energyConsumption(monitoredValue.getEnergyConsumption())
                .sensorId(monitoredValue.getSensor().getId())
                .build();
    }

    public static MonitoredValue toEntity(MonitoredValueDTO monitoredValueDTO) {

        return MonitoredValue.builder()
                .timestamp(new Timestamp(monitoredValueDTO.getTimestamp()))
                .energyConsumption(monitoredValueDTO.getMeasurementValue())
                .sensor(Sensor.builder().id(monitoredValueDTO.getSensorId()).build())
                .build();
    }
}
