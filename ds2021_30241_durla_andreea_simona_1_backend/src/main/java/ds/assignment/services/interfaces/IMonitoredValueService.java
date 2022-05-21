package ds.assignment.services.interfaces;

import ds.assignment.dtos.rest.MonitoredValueDTO;
import ds.assignment.dtos.rest.MonitoredValueDetailsDTO;
import ds.assignment.dtos.rest.StatisticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

public interface IMonitoredValueService {

    Page<MonitoredValueDetailsDTO> findMonitoredValues(Optional<UUID> sensorId, UUID clientId, LocalDate date, TimeZone timeZone, Pageable pageable);
    void insertMonitoredValue(MonitoredValueDTO monitoredValueDTO);
    StatisticsDTO findStatistics(Optional<UUID> sensorId, UUID clientId, LocalDate date, TimeZone timeZone);
}
