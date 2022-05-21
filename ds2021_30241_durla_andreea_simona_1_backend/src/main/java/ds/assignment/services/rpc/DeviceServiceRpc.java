package ds.assignment.services.rpc;

import ds.assignment.dtos.rpc.DeviceDTO;
import ds.assignment.dtos.rpc.builders.DeviceBuilder;
import ds.assignment.entities.Device;
import ds.assignment.exceptions.models.ResourceNotFoundException;
import ds.assignment.repositories.DeviceRepository;
import ds.assignment.services.rpc.interfaces.IDeviceServiceRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceServiceRpc implements IDeviceServiceRpc {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<DeviceDTO> findDevicesByClientId(UUID clientId) {

        List<Device> devices = deviceRepository.findByClientId(clientId);

        return devices.stream()
                .filter(d -> d.getSensor() != null)
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public float getDeviceMaxEnergyConsumption(UUID deviceId) {

        Optional<Device> device = deviceRepository.findById(deviceId);

        if(!device.isPresent())
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceId);

        return device.get().getMaxEnergyConsumption();
    }
}
