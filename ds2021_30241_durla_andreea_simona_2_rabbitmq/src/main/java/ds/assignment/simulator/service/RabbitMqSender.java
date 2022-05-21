package ds.assignment.simulator.service;

import ds.assignment.simulator.dtos.MonitoredValueDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${queue.rabbitmq.exchange}")
    private String exchange;

    @Value("${queue.rabbitmq.routingkey.sensor}")
    private String routingKey;

    public void send(MonitoredValueDTO payload) {
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
    }
}
