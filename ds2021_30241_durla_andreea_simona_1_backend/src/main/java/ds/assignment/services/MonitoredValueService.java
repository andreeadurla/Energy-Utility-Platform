package ds.assignment.services;

import ds.assignment.dtos.rest.EnergyConsumptionDTO;
import ds.assignment.dtos.rest.MonitoredValueDTO;
import ds.assignment.dtos.rest.MonitoredValueDetailsDTO;
import ds.assignment.dtos.rest.StatisticsDTO;
import ds.assignment.dtos.rest.builders.MonitoredValueBuilder;
import ds.assignment.entities.MonitoredValue;
import ds.assignment.entities.Sensor;
import ds.assignment.exceptions.models.ResourceNotFoundException;
import ds.assignment.repositories.MonitoredValueRepository;
import ds.assignment.services.interfaces.IMonitoredValueService;
import ds.assignment.utils.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Service
public class MonitoredValueService implements IMonitoredValueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoredValueService.class);

    @Autowired
    private MonitoredValueRepository monitoredValueRepository;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void insertMonitoredValue(MonitoredValueDTO monitoredValueDTO) {

        MonitoredValue monitoredValue = MonitoredValueBuilder.toEntity(monitoredValueDTO);

        try {
            computePowerPeak(monitoredValueDTO);
            monitoredValueRepository.save(monitoredValue);
        } catch(DataIntegrityViolationException ex) {
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with id: " + monitoredValueDTO.getSensorId());
        }
    }

    private void computePowerPeak(MonitoredValueDTO monitoredValueDTO) {
        float sensorMaxValue = sensorService.getSensorMaxValue(monitoredValueDTO.getSensorId());

        Optional<MonitoredValue> optionalLastMonitoredValue = monitoredValueRepository.findTopBySensorIdAndDeletedFalseOrderByTimestampDesc(monitoredValueDTO.getSensorId());

        if(!optionalLastMonitoredValue.isPresent())
            return ;

        MonitoredValue lastMonitoredValue = optionalLastMonitoredValue.get();

        float powerDiff = monitoredValueDTO.getMeasurementValue() - lastMonitoredValue.getEnergyConsumption();
        float timeDiffInHours = (monitoredValueDTO.getTimestamp() - lastMonitoredValue.getTimestamp().getTime()) / (float) 3600000;

        float powerConsumption = powerDiff / timeDiffInHours;

        try{
            if(powerConsumption > sensorMaxValue) {
                UUID clientId = sensorService.getAssignedClient(monitoredValueDTO.getSensorId());
                EnergyConsumptionDTO energyConsumptionDTO =
                        EnergyConsumptionDTO.builder()
                                .timestamp(monitoredValueDTO.getTimestamp())
                                .energyConsumption(powerConsumption)
                                .sensorId(monitoredValueDTO.getSensorId())
                                .build();

                sendLimitsExceededNotification(clientId, energyConsumptionDTO);
            }
        } catch (ResourceNotFoundException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public Page<MonitoredValueDetailsDTO> findMonitoredValues(
            Optional<UUID> sensorId,
            UUID clientId,
            LocalDate date,
            TimeZone timeZone,
            Pageable pageable) {

        Timestamp startDate = ApplicationUtils.getStartOfDay(date, timeZone);
        Timestamp endDate = ApplicationUtils.getEndOfDay(date, timeZone);

        Page<MonitoredValue> monitoredValues;

        if(sensorId.isPresent())
            monitoredValues = monitoredValueRepository
                    .findAllBySensorIdAndTimestampBetweenAndDeletedIsFalseOrderByTimestamp(sensorId.get(), startDate, endDate, pageable);
        else {
            List<UUID> sensorsId = sensorService.getSensorsIdByClientId(clientId);
            monitoredValues = monitoredValueRepository
                    .findAllByTimestampBetweenAndDeletedIsFalseAndSensorIdInOrderByTimestamp(startDate, endDate, sensorsId, pageable);
        }

        return monitoredValues.map(MonitoredValueBuilder::toMonitoredValueDetailsDTO);
    }

    public StatisticsDTO findStatistics(Optional<UUID> sensorId, UUID clientId, LocalDate date, TimeZone timeZone) {

        if(sensorId.isPresent())
          return getStatisticsForSensorId(sensorId.get(), date, timeZone);

        return getStatistics(clientId, date, timeZone);
    }

    private StatisticsDTO getStatisticsForSensorId(UUID sensorId, LocalDate date, TimeZone timeZone) {

        Optional<MonitoredValue> previousMonitoredValue = getLastMonitoredValueOfPreviousDay(sensorId, date, timeZone);

        Timestamp startDate = ApplicationUtils.getStartOfDay(date, timeZone);
        Timestamp endDate = ApplicationUtils.getEndOfDay(date, timeZone);

        List<MonitoredValue> monitoredValues =
                monitoredValueRepository.findAllBySensorIdAndTimestampBetweenAndDeletedIsFalseOrderByTimestamp(sensorId, startDate, endDate);

        float statistics[] = computeStatistics(previousMonitoredValue, monitoredValues, timeZone);

        return StatisticsDTO.builder().statisticsOnHours(statistics).build();
    }

    private StatisticsDTO getStatistics(UUID clientId, LocalDate date, TimeZone timeZone) {

        List<UUID> sensorsId = sensorService.getSensorsIdByClientId(clientId);

        Timestamp startDate = ApplicationUtils.getStartOfDay(date, timeZone);
        Timestamp endDate = ApplicationUtils.getEndOfDay(date, timeZone);

        float statistics[] = new float[24];

        for(UUID sensorId : sensorsId) {
            Optional<MonitoredValue> previousMonitoredValue = getLastMonitoredValueOfPreviousDay(sensorId, date, timeZone);

            List<MonitoredValue> monitoredValues =
                    monitoredValueRepository.findAllBySensorIdAndTimestampBetweenAndDeletedIsFalseOrderByTimestamp(sensorId, startDate, endDate);

            float sensorStatistics[] = computeStatistics(previousMonitoredValue, monitoredValues, timeZone);

            for(int i = 0; i < 24; i++)
                statistics[i] += sensorStatistics[i];
        }

        return StatisticsDTO.builder().statisticsOnHours(statistics).build();
    }

    public Optional<MonitoredValue> getLastMonitoredValueOfPreviousDay(UUID sensorId, LocalDate date, TimeZone timeZone) {

        LocalDate previousDate = date.minusDays(1);
        Timestamp startDate = ApplicationUtils.getStartOfDay(previousDate, timeZone);
        Timestamp endDate = ApplicationUtils.getEndOfDay(previousDate, timeZone);

        return monitoredValueRepository
                    .findTopBySensorIdAndTimestampBetweenAndDeletedFalseOrderByTimestampDesc(sensorId,
                                                                                            startDate,
                                                                                            endDate);
    }

    public float[] computeStatistics(
            Optional<MonitoredValue> previousMonitoredValue,
            List<MonitoredValue> monitoredValues,
            TimeZone timeZone) {

        float statistics[] = new float[24];
        int offset = timeZone.getOffset(new Date().getTime()) / 1000 / 3600;

        if(monitoredValues.isEmpty())
            return statistics;

        int size = monitoredValues.size();

        int hour = monitoredValues.get(0).getTimestamp().toLocalDateTime().getHour();
        float energyConsumption;

        if(!previousMonitoredValue.isPresent())
            energyConsumption = monitoredValues.get(0).getEnergyConsumption();
        else {
            float powerDiff = monitoredValues.get(0).getEnergyConsumption() - previousMonitoredValue.get().getEnergyConsumption();
            float timeDiffInHours = (monitoredValues.get(0).getTimestamp().getTime() - previousMonitoredValue.get().getTimestamp().getTime()) / (float) 3600000;
            energyConsumption = powerDiff / timeDiffInHours;
        }

        statistics[(hour + offset) % 24] += energyConsumption;

        for(int i = 1; i < size; i++) {
            hour = monitoredValues.get(i).getTimestamp().toLocalDateTime().getHour();

            float powerDiff = monitoredValues.get(i).getEnergyConsumption() - monitoredValues.get(i - 1).getEnergyConsumption();
            float timeDiffInHours = (monitoredValues.get(i).getTimestamp().getTime() - monitoredValues.get(i - 1).getTimestamp().getTime()) / (float) 3600000;
            energyConsumption = powerDiff / timeDiffInHours;

            statistics[(hour + offset) % 24] += energyConsumption;
        }

        return statistics;
    }

    private void sendLimitsExceededNotification(UUID clientId, EnergyConsumptionDTO energyConsumptionDTO) {
        this.messagingTemplate.convertAndSendToUser(clientId.toString(), "/queue/notification", energyConsumptionDTO);
    }
}
