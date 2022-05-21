package ds.assignment.dtos.rest.builders;

import ds.assignment.dtos.rest.SensorDTO;
import ds.assignment.dtos.rest.SensorDetailsDTO;
import ds.assignment.entities.Device;
import ds.assignment.entities.Sensor;

public class SensorBuilder {

    private SensorBuilder() {}

    public static SensorDetailsDTO toSensorDetailsDTO(Sensor sensor) {
        return SensorDetailsDTO.builder()
                .id(sensor.getId())
                .description(sensor.getDescription())
                .maxValue(sensor.getMaxValue())
                .isAssigned(sensor.getDevice() != null)
                .build();
    }

    public static Sensor toEntity(SensorDTO sensorDTO) {
        return Sensor.builder()
                .description(sensorDTO.getDescription())
                .maxValue(sensorDTO.getMaxValue())
                .device((sensorDTO.getDeviceId() != null) ?
                        Device.builder().id(sensorDTO.getDeviceId()).build():
                        null
                )
                .build();
    }
}
