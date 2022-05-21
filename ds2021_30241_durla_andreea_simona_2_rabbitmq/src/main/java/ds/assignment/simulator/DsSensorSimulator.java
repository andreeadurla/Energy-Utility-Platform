package ds.assignment.simulator;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRabbit
public class DsSensorSimulator extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DsSensorSimulator.class);
    }

    public static void main(String[] args) {
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(DsSensorSimulator.class, args);
    }
}
