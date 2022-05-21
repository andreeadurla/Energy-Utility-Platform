package ds.assignment.rpc.configuration;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import ds.assignment.rpc.services.interfaces.IDeviceServiceRpc;
import ds.assignment.rpc.services.interfaces.IEnergyConsumptionServiceRpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class JsonRpcConfiguration {

    @Value("${baseUrl}")
    private String baseUrl;

    @Bean
    public IDeviceServiceRpc iDeviceService() throws MalformedURLException {

        JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(baseUrl + "/rpc/devices"));
        IDeviceServiceRpc iDeviceService = ProxyUtil.createClientProxy(
                getClass().getClassLoader(),
                IDeviceServiceRpc.class,
                client
        );

        return iDeviceService;
    }

    @Bean
    public IEnergyConsumptionServiceRpc iEnergyConsumptionService() throws MalformedURLException {

        JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(baseUrl + "/rpc/energy-consumption"));
        IEnergyConsumptionServiceRpc iEnergyConsumptionService = ProxyUtil.createClientProxy(
                getClass().getClassLoader(),
                IEnergyConsumptionServiceRpc.class,
                client
        );

        return iEnergyConsumptionService;
    }
}
