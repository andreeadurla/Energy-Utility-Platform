package ds.assignment.simulator.simulator;

import ds.assignment.simulator.dtos.MonitoredValueDTO;
import ds.assignment.simulator.service.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
import java.util.UUID;

@Component
public class SensorSimulator {

    @Value("${simulator.file-path}")
    private String csvFilePath;
    private UUID sensorId;
    private BufferedReader csvReader;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    private LocalDateTime localDateTime = LocalDateTime.now().minusDays(3).minusHours(5);

    public SensorSimulator(@Value("${sensor.id}") UUID sensorId) {
        this.sensorId = sensorId;
    }

    @PostConstruct
    public void init() throws FileNotFoundException {
        this.csvReader = new BufferedReader(new FileReader(csvFilePath));
    }

    public void registerNextValue() {

        try {
            String energyConsumptionStr = csvReader.readLine();
            if(energyConsumptionStr != null) {
                //Instant instant = Instant.now();
                float energyConsumption = Float.parseFloat(energyConsumptionStr);

                //LocalDateTime dateTime = LocalDateTime.now();
                LocalDateTime dateTime = localDateTime.plusHours(1);
                localDateTime = dateTime;

                //Timestamp timestamp = Timestamp.from(instant);
                Timestamp timestamp = Timestamp.valueOf(dateTime);
                System.out.println(timestamp);
                MonitoredValueDTO monitoredValue = new MonitoredValueDTO(timestamp.getTime(),
                                                                energyConsumption,
                                                                sensorId);
                rabbitMqSender.send(monitoredValue);
                System.out.println(monitoredValue);
            }
            else {
                csvReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
