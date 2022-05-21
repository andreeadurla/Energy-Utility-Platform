package ds.assignment.dtos.rpc.builders;

import ds.assignment.dtos.rpc.DeviceDTO;
import ds.assignment.entities.Device;

public class DeviceBuilder {

    private DeviceBuilder() {}

    public static DeviceDTO toDeviceDTO(Device device) {
        return DeviceDTO.builder()
                .id(device.getId())
                .description(device.getDescription())
                .build();
    }
}
