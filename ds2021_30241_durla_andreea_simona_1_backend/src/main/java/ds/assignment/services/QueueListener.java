package ds.assignment.services;

import ds.assignment.dtos.rest.MonitoredValueDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueListener {

    private MonitoredValueService monitoredValueService;

    @Autowired
    public QueueListener(MonitoredValueService monitoredValueService) {
        this.monitoredValueService = monitoredValueService;
    }

    @RabbitListener(queues = "${queue.rabbitmq.queue.sensor}")
    public void listen(MonitoredValueDTO payload) {
        System.out.println(payload.toString());
        monitoredValueService.insertMonitoredValue(payload);
    }
}
