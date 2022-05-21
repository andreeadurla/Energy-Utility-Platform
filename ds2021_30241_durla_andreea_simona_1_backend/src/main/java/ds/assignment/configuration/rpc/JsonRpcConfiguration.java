package ds.assignment.configuration.rpc;

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import ds.assignment.services.rpc.DeviceServiceRpc;
import ds.assignment.services.rpc.EnergyConsumptionServiceRpc;
import ds.assignment.services.rpc.interfaces.IDeviceServiceRpc;
import ds.assignment.services.rpc.interfaces.IEnergyConsumptionServiceRpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonRpcConfiguration {

    @Bean
    public IDeviceServiceRpc iDeviceService() {
        return new DeviceServiceRpc();
    }

    @Bean
    public IEnergyConsumptionServiceRpc iEnergyConsumptionService() {
        return new EnergyConsumptionServiceRpc();
    }

    @Bean(name = "/rpc/devices")
    public JsonServiceExporter deviceServiceExporter() {
        JsonServiceExporter exporter = new JsonServiceExporter();
        exporter.setService(iDeviceService());
        exporter.setServiceInterface(IDeviceServiceRpc.class);
        return exporter;
    }

    @Bean(name = "/rpc/energy-consumption")
    public JsonServiceExporter energyConsumptionServiceExporter() {
        JsonServiceExporter exporter = new JsonServiceExporter();
        exporter.setService(iEnergyConsumptionService());
        exporter.setServiceInterface(IEnergyConsumptionServiceRpc.class);
        return exporter;
    }
}
