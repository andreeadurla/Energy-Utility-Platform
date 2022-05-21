package ds.assignment.rpc.services.interfaces;

import ds.assignment.rpc.dtos.BaselineDTO;
import ds.assignment.rpc.dtos.EnergyConsumptionDTO;
import ds.assignment.rpc.dtos.ProgramSelectionDTO;

import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public interface IEnergyConsumptionServiceRpc {

    List<EnergyConsumptionDTO> getHistoricalEnergyConsumption(UUID clientId, int days, TimeZone timeZone);

    BaselineDTO getAveragedEnergyConsumption(UUID clientId, int days, TimeZone timeZone);

    ProgramSelectionDTO getProgramBestStartingTime(UUID clientId, UUID deviceId, int days, int duration, TimeZone timeZone);
}
