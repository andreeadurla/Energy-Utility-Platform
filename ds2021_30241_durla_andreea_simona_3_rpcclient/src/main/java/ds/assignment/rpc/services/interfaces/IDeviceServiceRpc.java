package ds.assignment.rpc.services.interfaces;

import ds.assignment.rpc.dtos.DeviceDTO;

import java.util.List;
import java.util.UUID;

public interface IDeviceServiceRpc {

    List<DeviceDTO> findDevicesByClientId(UUID clientId);
}
