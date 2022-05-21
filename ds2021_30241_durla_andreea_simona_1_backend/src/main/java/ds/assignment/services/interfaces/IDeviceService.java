package ds.assignment.services.interfaces;

import ds.assignment.dtos.rest.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IDeviceService {

    List<DeviceDetailsDTO> findDevices();
    Page<DeviceDetailsDTO> findDevices(Pageable pageable);
    DeviceDetailsDTO findDeviceById(UUID id);
    List<DeviceDetailsDTO> findDevicesByClientId(UUID clientId);
    List<DeviceMoreDetailsDTO> findDevicesWithMoreDetailsByClientId(UUID clientId);
    List<DeviceSomeDetailsDTO> findDevicesWithSomeDetailsByClientId(UUID clientId);
    List<DeviceDetailsDTO> findUnassignedDevices();
    UUID insertDevice(DeviceDTO deviceDTO);
    void assignClientToDevices(AssignClientToDevicesDTO assignClientToDevicesDTO);
    void unassignClient(UUID clientId);
    void updateDevice(UUID id, DeviceDTO deviceDTO);
    void deleteDeviceById(UUID id);
}
