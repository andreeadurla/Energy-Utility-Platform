package ds.assignment.services;

import ds.assignment.dtos.rest.AssignSensorToDeviceDTO;
import ds.assignment.dtos.rest.SensorDTO;
import ds.assignment.dtos.rest.SensorDetailsDTO;
import ds.assignment.dtos.rest.builders.SensorBuilder;
import ds.assignment.entities.Client;
import ds.assignment.entities.Device;
import ds.assignment.entities.Sensor;
import ds.assignment.exceptions.models.ResourceNotFoundException;
import ds.assignment.repositories.DeviceRepository;
import ds.assignment.repositories.MonitoredValueRepository;
import ds.assignment.repositories.SensorRepository;
import ds.assignment.services.interfaces.ISensorService;
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
public class SensorService implements ISensorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);

    private static SensorRepository sensorRepository;
    private static DeviceRepository deviceRepository;
    private static MonitoredValueRepository monitoredValueRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository,
                         DeviceRepository deviceRepository,
                         MonitoredValueRepository monitoredValueRepository) {
        this.sensorRepository = sensorRepository;
        this.deviceRepository = deviceRepository;
        this.monitoredValueRepository = monitoredValueRepository;
    }

    public List<SensorDetailsDTO> findSensors() {
        List<Sensor> sensors = sensorRepository.findAll();

        return sensors.stream()
                .map(SensorBuilder::toSensorDetailsDTO)
                .collect(Collectors.toList());
    }

    public Page<SensorDetailsDTO> findSensors(Pageable pageable) {
        Page<Sensor> sensors = sensorRepository.findAll(pageable);

        return sensors.map(SensorBuilder::toSensorDetailsDTO);
    }

    public SensorDetailsDTO findSensorById(UUID id) {

        Optional<Sensor> sensorOptional = sensorRepository.findById(id);

        if(!sensorOptional.isPresent()) {
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with id: " + id);
        }

        return SensorBuilder.toSensorDetailsDTO(sensorOptional.get());
    }

    public SensorDetailsDTO findSensorByDeviceId(UUID deviceId) {

        Optional<Sensor> sensorOptional = sensorRepository.findByDeviceId(deviceId);

        if(!sensorOptional.isPresent()) {
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with device id: " + deviceId);
        }

        return SensorBuilder.toSensorDetailsDTO(sensorOptional.get());
    }

    public List<SensorDetailsDTO> findUnassignedSensors() {

        List<Sensor> sensors = sensorRepository.findUnassignedSensors();

        return sensors.stream()
                .map(SensorBuilder::toSensorDetailsDTO)
                .collect(Collectors.toList());
    }

    public UUID insertSensor(SensorDTO sensorDTO) {

        Sensor sensor = SensorBuilder.toEntity(sensorDTO);

        try {
            sensor = sensorRepository.save(sensor);
        } catch(DataIntegrityViolationException ex) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + sensorDTO.getDeviceId());
        }

        return sensor.getId();
    }

    public void assignDeviceToSensor(AssignSensorToDeviceDTO assignSensorToDeviceDTO) {

        UUID deviceId = assignSensorToDeviceDTO.getDeviceId();
        UUID newSensorId = assignSensorToDeviceDTO.getNewSensorId();
        UUID removedSensorId = assignSensorToDeviceDTO.getRemovedSensorId();

        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);

        if(!deviceOptional.isPresent()) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceId);
        }

        if(removedSensorId != null) {
            sensorRepository.findById(removedSensorId)
                    .map(sensor -> {
                        sensor.setDevice(null);
                        return sensorRepository.save(sensor);
                    });
        }

        if(newSensorId != null) {
            sensorRepository.findById(newSensorId)
                    .map(sensor -> {
                        sensor.setDevice(Device.builder().id(deviceId).build());
                        return sensorRepository.save(sensor);
                    });
        }
    }

    public void updateSensor(UUID id, SensorDTO sensorDTO) {

        sensorRepository.findById(id)
                .map(sensor -> {
                    sensor.setDescription(sensorDTO.getDescription());
                    sensor.setMaxValue(sensorDTO.getMaxValue());
                    return sensorRepository.save(sensor);
                })
                .orElseGet(() -> {
                    throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with id: " + id);
                });
    }

    public void deleteSensorById(UUID id) {

        try {
            monitoredValueRepository.deleteBySensorId(id);
            sensorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with id: " + id);
        }
    }

    public void unassignDevice(UUID deviceId) {
        sensorRepository.unassignDevice(deviceId);
    }

    public List<UUID> getSensorsIdByClientId(UUID clientId) {

        List<Sensor> sensors = sensorRepository.findByClientId(clientId);

        return sensors.stream().map(s -> s.getId()).collect(Collectors.toList());
    }

    public float getSensorMaxValue(UUID id) {

        Optional<Sensor> sensorOptional = sensorRepository.findById(id);

        if(!sensorOptional.isPresent()) {
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with id: " + id);
        }

        return sensorOptional.get().getMaxValue();
    }

    public UUID getAssignedClient(UUID sensorId) {

        Optional<Sensor> sensorOptional = sensorRepository.findById(sensorId);

        if(!sensorOptional.isPresent()) {
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with id: " + sensorId);
        }

        Device device = sensorOptional.get().getDevice();
        if(device == null) {
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with id: " + sensorId + " has no device assigned");
        }

        Client client = device.getClient();
        if(client == null) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + device.getId() + " has no client assigned");
        }

        return client.getId();
    }

}
