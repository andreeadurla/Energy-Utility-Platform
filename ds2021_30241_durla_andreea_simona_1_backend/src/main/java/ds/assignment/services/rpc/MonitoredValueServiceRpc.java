package ds.assignment.services.rpc;

import ds.assignment.dtos.rpc.EnergyConsumptionDTO;
import ds.assignment.entities.MonitoredValue;
import ds.assignment.repositories.MonitoredValueRepository;
import ds.assignment.services.MonitoredValueService;
import ds.assignment.services.SensorService;
import ds.assignment.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MonitoredValueServiceRpc {

    @Autowired
    private MonitoredValueRepository monitoredValueRepository;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private MonitoredValueService monitoredValueService;

    public List<EnergyConsumptionDTO> getEnergyConsumption(UUID clientId, int days, TimeZone timeZone) {

        int offset = timeZone.getOffset(new Date().getTime()) / 1000 / 3600;

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDate currentDate = currentDateTime.plusMinutes(offset).toLocalDate();

        List<EnergyConsumptionDTO> energyConsumptionOverNDays = new ArrayList<>();
        List<UUID> sensorsId = sensorService.getSensorsIdByClientId(clientId);

        for(int i = days; i >= 1; i--) {

            LocalDate date = currentDate.minusDays(i);

            float[] energyConsumption = getEnergyConsumption(sensorsId, date, timeZone);
            energyConsumptionOverNDays.add(
                    EnergyConsumptionDTO.builder()
                            .date(ApplicationUtils.formatLocalDate(date))
                            .energyConsumption(energyConsumption)
                            .build()
            );
        }

        return energyConsumptionOverNDays;
    }

    private float[] getEnergyConsumption(List<UUID> sensorsId, LocalDate date, TimeZone timeZone) {

        float statistics[] = new float[24];

        Timestamp startDate = ApplicationUtils.getStartOfDay(date, timeZone);
        Timestamp endDate = ApplicationUtils.getEndOfDay(date, timeZone);

        for(UUID sensorId: sensorsId) {
            Optional<MonitoredValue> previousMonitoredValue =
                    monitoredValueService.getLastMonitoredValueOfPreviousDay(sensorId, date, timeZone);

            List<MonitoredValue> monitoredValues =
                    monitoredValueRepository.findAllBySensorIdAndTimestampBetweenAndDeletedIsFalseOrderByTimestamp(sensorId, startDate, endDate);

            float sensorStatistics[] =
                    monitoredValueService.computeStatistics(previousMonitoredValue, monitoredValues, timeZone);

            for(int i = 0; i < 24; i++)
                statistics[i] += sensorStatistics[i];
        }

        return statistics;
    }
}
