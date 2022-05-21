package ds.assignment.services.rpc.interfaces;

import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import ds.assignment.dtos.rpc.DeviceDTO;

import java.util.List;
import java.util.UUID;

public interface IDeviceServiceRpc {

    List<DeviceDTO> findDevicesByClientId(UUID clientId);

    float getDeviceMaxEnergyConsumption(UUID deviceId);
}
