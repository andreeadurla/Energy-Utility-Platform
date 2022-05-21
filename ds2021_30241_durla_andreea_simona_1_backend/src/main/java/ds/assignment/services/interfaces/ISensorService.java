package ds.assignment.services.interfaces;

import ds.assignment.dtos.rest.AssignSensorToDeviceDTO;
import ds.assignment.dtos.rest.SensorDTO;
import ds.assignment.dtos.rest.SensorDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ISensorService {

    List<SensorDetailsDTO> findSensors();
    Page<SensorDetailsDTO> findSensors(Pageable pageable);
    SensorDetailsDTO findSensorById(UUID id);
    SensorDetailsDTO findSensorByDeviceId(UUID clientId);
    List<SensorDetailsDTO> findUnassignedSensors();
    UUID insertSensor(SensorDTO sensorDTO);
    void assignDeviceToSensor(AssignSensorToDeviceDTO assignSensorToDeviceDTO);
    void unassignDevice(UUID deviceId);
    void updateSensor(UUID id, SensorDTO sensorDTO);
    void deleteSensorById(UUID id);
    List<UUID> getSensorsIdByClientId(UUID clientId);
    float getSensorMaxValue(UUID sensorId);
}
