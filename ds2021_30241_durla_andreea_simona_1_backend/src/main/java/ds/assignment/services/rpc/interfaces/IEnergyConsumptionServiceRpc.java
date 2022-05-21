package ds.assignment.services.rpc.interfaces;

import ds.assignment.dtos.rpc.BaselineDTO;
import ds.assignment.dtos.rpc.EnergyConsumptionDTO;
import ds.assignment.dtos.rpc.ProgramSelectionDTO;

import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public interface IEnergyConsumptionServiceRpc {

    List<EnergyConsumptionDTO> getHistoricalEnergyConsumption(UUID clientId, int days, TimeZone timeZone);

    BaselineDTO getAveragedEnergyConsumption(UUID clientId, int days, TimeZone timeZone);

    ProgramSelectionDTO getProgramBestStartingTime(UUID clientId, UUID deviceId, int days, int duration, TimeZone timeZone);
}
