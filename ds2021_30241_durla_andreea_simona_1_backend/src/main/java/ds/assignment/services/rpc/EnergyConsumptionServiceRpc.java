package ds.assignment.services.rpc;

import ds.assignment.dtos.rpc.BaselineDTO;
import ds.assignment.dtos.rpc.EnergyConsumptionDTO;
import ds.assignment.dtos.rpc.ProgramSelectionDTO;
import ds.assignment.exceptions.models.ResourceNotFoundException;
import ds.assignment.services.rpc.interfaces.IEnergyConsumptionServiceRpc;
import ds.assignment.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class EnergyConsumptionServiceRpc implements IEnergyConsumptionServiceRpc {

    @Autowired
    private MonitoredValueServiceRpc monitoredValueService;

    @Autowired
    private DeviceServiceRpc deviceService;

    @Override
    public List<EnergyConsumptionDTO> getHistoricalEnergyConsumption(UUID clientId, int days, TimeZone timeZone) {
        return monitoredValueService.getEnergyConsumption(clientId, days, timeZone);
    }

    @Override
    public BaselineDTO getAveragedEnergyConsumption(UUID clientId, int days, TimeZone timeZone) {
        List<EnergyConsumptionDTO> historicalEnergyConsumption = monitoredValueService.getEnergyConsumption(clientId, days, timeZone);

        float[] averagedEnergyConsumption = new float[24];

        for(int i = 0; i < 24; i++) {
            float sum = 0;

            for(EnergyConsumptionDTO energyConsumptionDTO: historicalEnergyConsumption) {
                float[] energyConsumption = energyConsumptionDTO.getEnergyConsumption();
                sum += energyConsumption[i];
            }

            averagedEnergyConsumption[i] = sum / days;
        }

        return BaselineDTO.builder()
                .startDate(historicalEnergyConsumption.get(0).getDate())
                .endDate(historicalEnergyConsumption.get(days-1).getDate())
                .averagedEnergyConsumption(averagedEnergyConsumption)
                .build();
    }

    @Override
    public ProgramSelectionDTO getProgramBestStartingTime(UUID clientId, UUID deviceId, int days, int duration, TimeZone timeZone) {

        BaselineDTO baselineDTO = getAveragedEnergyConsumption(clientId, days, timeZone);
        float[] averagedConsumption = baselineDTO.getAveragedEnergyConsumption();

        float deviceMaxEnergy;

        try {
            deviceMaxEnergy = deviceService.getDeviceMaxEnergyConsumption(deviceId);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }

        int startHour = getBestStartingHour(averagedConsumption, duration);

        float[] estimatedConsumption = new float[24];
        for(int i = 0; i < 24; i++) {
            if(i >= startHour && i < startHour + duration)
                estimatedConsumption[i] = averagedConsumption[i] + deviceMaxEnergy;
            else
                estimatedConsumption[i] = averagedConsumption[i];
        }

        LocalDate nextDate = LocalDate.now();
        LocalDateTime startTime = nextDate.atTime(startHour, 0);
        LocalDateTime endTime = nextDate.atTime((startHour + duration) % 24, 0)
                .plusDays((startHour + duration) / 24);

        return ProgramSelectionDTO.builder()
                .startTime(ApplicationUtils.formatLocalDateTime(startTime))
                .endTime(ApplicationUtils.formatLocalDateTime(endTime))
                .averagedEnergyConsumption(averagedConsumption)
                .estimatedConsumption(estimatedConsumption)
                .build();
    }

    private int getBestStartingHour(float[] baseline, int duration) {

        int size = 24 - duration + 1;

        float min = Float.MAX_VALUE;
        int startHour = 0;

        for(int i = 0; i < size; i++) {
            float max = 0;

            for(int j = 0; j < duration; j++) {
                int pos = i + j;
                if(baseline[pos] > max)
                    max = baseline[pos];
            }

            if(max < min) {
                min = max;
                startHour = i;
            }
        }

        return startHour;
    }

}
