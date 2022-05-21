package ds.assignment.services;

import ds.assignment.dtos.rest.*;
import ds.assignment.dtos.rest.builders.DeviceBuilder;
import ds.assignment.entities.Client;
import ds.assignment.entities.Device;
import ds.assignment.exceptions.models.ResourceNotFoundException;
import ds.assignment.repositories.ClientRepository;
import ds.assignment.repositories.DeviceRepository;
import ds.assignment.services.interfaces.IDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService implements IDeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    private static DeviceRepository deviceRepository;
    private static ClientRepository clientRepository;
    private static SensorService sensorService;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository,
                         ClientRepository clientRepository,
                         SensorService sensorService) {
        this.deviceRepository = deviceRepository;
        this.clientRepository = clientRepository;
        this.sensorService = sensorService;
    }

    public List<DeviceDetailsDTO> findDevices() {

        List<Device> devices = deviceRepository.findAll();

        return devices.stream()
                .map(DeviceBuilder::toDeviceDetailsDTO)
                .collect(Collectors.toList());
    }

    public Page<DeviceDetailsDTO> findDevices(Pageable pageable) {

        Page<Device> devices = deviceRepository.findAll(pageable);

        return devices.map(DeviceBuilder::toDeviceDetailsDTO);
    }

    public DeviceDetailsDTO findDeviceById(UUID id) {

        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if(!deviceOptional.isPresent()) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }

        return DeviceBuilder.toDeviceDetailsDTO(deviceOptional.get());
    }

    public List<DeviceDetailsDTO> findDevicesByClientId(UUID clientId) {

        List<Device> devices = deviceRepository.findByClientId(clientId);

        return devices.stream()
                .map(DeviceBuilder::toDeviceDetailsDTO)
                .collect(Collectors.toList());
    }

    public List<DeviceMoreDetailsDTO> findDevicesWithMoreDetailsByClientId(UUID clientId) {

        List<Device> devices = deviceRepository.findByClientId(clientId);

        return devices.stream()
                .filter(d -> d.getSensor() != null)
                .map(DeviceBuilder::toDeviceMoreDetailsDTO)
                .collect(Collectors.toList());
    }

    public List<DeviceSomeDetailsDTO> findDevicesWithSomeDetailsByClientId(UUID clientId) {

        List<Device> devices = deviceRepository.findByClientId(clientId);

        return devices.stream()
                .filter(d -> d.getSensor() != null)
                .map(DeviceBuilder::toDeviceSomeDetailsDTO)
                .collect(Collectors.toList());
    }

    public List<DeviceDetailsDTO> findUnassignedDevices() {

        List<Device> devices = deviceRepository.findUnassignedDevices();

        return devices.stream()
                .map(DeviceBuilder::toDeviceDetailsDTO)
                .collect(Collectors.toList());
    }

    public UUID insertDevice(DeviceDTO deviceDTO) {

        Device device = DeviceBuilder.toEntity(deviceDTO);

        try {
            device = deviceRepository.save(device);
        } catch(DataIntegrityViolationException ex) {
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + deviceDTO.getClientId());
        }

        return device.getId();
    }

    public void assignClientToDevices(AssignClientToDevicesDTO assignClientToDevicesDTO) {

        UUID clientId = assignClientToDevicesDTO.getClientId();
        List<UUID> newDevicesId = assignClientToDevicesDTO.getNewDevicesId();
        List<UUID> removedDevicesId = assignClientToDevicesDTO.getRemovedDevicesId();

        Optional<Client> client = clientRepository.findById(clientId);

        if(!client.isPresent()) {
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + clientId);
        }

        List<Device> devices =
                deviceRepository.findByIds(newDevicesId)
                                .stream()
                                .map(device -> {
                                    device.setClient(Client.builder().id(clientId).build());
                                    return device;
                                })
                                .collect(Collectors.toList());

        deviceRepository.saveAll(devices);

        devices = deviceRepository.findByIds(removedDevicesId)
                        .stream()
                        .map(device -> {
                            device.setClient(null);
                            return device;
                        })
                        .collect(Collectors.toList());

        deviceRepository.saveAll(devices);
    }

    public void updateDevice(UUID id, DeviceDTO deviceDTO) {

        deviceRepository.findById(id)
                .map(device -> {
                    device.setDescription(deviceDTO.getDescription());
                    device.setAddress(deviceDTO.getAddress());
                    device.setMaxEnergyConsumption(deviceDTO.getMaxEnergyConsumption());
                    device.setAvgEnergyConsumption(deviceDTO.getAvgEnergyConsumption());
                    return deviceRepository.save(device);
                })
                .orElseGet(() -> {
                    throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
                });
    }

    public void deleteDeviceById(UUID id) {

        try {
            deviceRepository.deleteById(id);
            sensorService.unassignDevice(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
    }

    public void unassignClient(UUID clientId) {
        deviceRepository.unassignClient(clientId);
    }
}
