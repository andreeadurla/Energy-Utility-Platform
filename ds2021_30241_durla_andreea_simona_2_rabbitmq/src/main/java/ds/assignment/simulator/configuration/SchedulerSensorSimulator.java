package ds.assignment.simulator.configuration;

import ds.assignment.simulator.simulator.SensorSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SchedulerSensorSimulator {

    @Autowired
    private SensorSimulator sensorSimulator;

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void scheduleRegisterEnergyConsumptionTask() {
        System.out.println("Scheduler sensor simulator");
        sensorSimulator.registerNextValue();
    }
}
