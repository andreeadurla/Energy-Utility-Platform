package ds.assignment.dtos.rest.builders;

import ds.assignment.dtos.rest.DeviceDTO;
import ds.assignment.dtos.rest.DeviceDetailsDTO;
import ds.assignment.dtos.rest.DeviceMoreDetailsDTO;
import ds.assignment.dtos.rest.DeviceSomeDetailsDTO;
import ds.assignment.entities.Client;
import ds.assignment.entities.Device;

public class DeviceBuilder {

    private DeviceBuilder() {}

    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device) {
        return DeviceDetailsDTO.builder()
                .id(device.getId())
                .description(device.getDescription())
                .address(device.getAddress())
                .maxEnergyConsumption(device.getMaxEnergyConsumption())
                .avgEnergyConsumption(device.getAvgEnergyConsumption())
                .isAssigned(device.getClient() != null)
                .build();
    }

    public static DeviceMoreDetailsDTO toDeviceMoreDetailsDTO(Device device) {
        return DeviceMoreDetailsDTO.builder()
                .id(device.getId())
                .description(device.getDescription())
                .address(device.getAddress())
                .maxEnergyConsumption(device.getMaxEnergyConsumption())
                .avgEnergyConsumption(device.getAvgEnergyConsumption())
                .sensorDetails(SensorBuilder.toSensorDetailsDTO(device.getSensor()))
                .build();
    }

    public static DeviceSomeDetailsDTO toDeviceSomeDetailsDTO(Device device) {
        return DeviceSomeDetailsDTO.builder()
                .id(device.getId())
                .description(device.getDescription())
                .sensorId(device.getSensor().getId())
                .build();
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return Device.builder()
                .description(deviceDTO.getDescription())
                .address(deviceDTO.getAddress())
                .maxEnergyConsumption(deviceDTO.getMaxEnergyConsumption())
                .avgEnergyConsumption(deviceDTO.getAvgEnergyConsumption())
                .client((deviceDTO.getClientId() != null) ?
                        Client.builder().id(deviceDTO.getClientId()).build():
                        null
                )
                .build();
    }
}