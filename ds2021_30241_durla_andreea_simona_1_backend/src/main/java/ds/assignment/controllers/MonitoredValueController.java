package ds.assignment.controllers;

import ds.assignment.dtos.rest.MonitoredValueDetailsDTO;
import ds.assignment.dtos.rest.StatisticsDTO;
import ds.assignment.services.interfaces.IMonitoredValueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/monitored-values")
public class MonitoredValueController {

    private final IMonitoredValueService monitoredValueService;

    public MonitoredValueController(IMonitoredValueService monitoredValueService) {
        this.monitoredValueService = monitoredValueService;
    }

    @GetMapping
    public ResponseEntity<Page<MonitoredValueDetailsDTO>> getMonitoredValues(@RequestParam(value = "sensor-id") Optional<UUID> sensorId,
                                                                             @RequestParam(value = "client-id") UUID clientId,
                                                                             @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                                                             @RequestParam(value = "timeZone") String zone,
                                                                             Pageable pageable) {

        TimeZone timeZone = TimeZone.getTimeZone(zone);
        Page<MonitoredValueDetailsDTO> monitoredValueDTOS = monitoredValueService.findMonitoredValues(sensorId, clientId, date, timeZone, pageable);

        return new ResponseEntity<>(monitoredValueDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/statistics")
    public ResponseEntity<StatisticsDTO> getMonitoredValuesForStatistics(@RequestParam(value = "sensor-id", required = false) Optional<UUID> sensorId,
                                                                         @RequestParam(value = "client-id") UUID clientId,
                                                                         @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                                                         @RequestParam(value = "timeZone") String zone) {

        TimeZone timeZone = TimeZone.getTimeZone(zone);
        StatisticsDTO statisticsDTO = monitoredValueService.findStatistics(sensorId, clientId, date, timeZone);

        return new ResponseEntity<>(statisticsDTO, HttpStatus.OK);
    }
}
