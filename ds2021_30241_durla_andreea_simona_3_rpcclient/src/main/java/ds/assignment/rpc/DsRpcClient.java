package ds.assignment.rpc;

import ds.assignment.rpc.views.authentication.AuthenticationController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.TimeZone;

@SpringBootApplication
public class DsRpcClient extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DsRpcClient.class);
    }

    public static void main(String[] args) {
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DsRpcClient.class)
                .headless(false)
                .run(args);

        AuthenticationController authController = context.getBean(AuthenticationController.class);
        authController.initView();
    }
}
