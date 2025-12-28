package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "iot")
public class IotProperties {

    private Map<String, Long> gatewayDeviceMap = new HashMap<>();

    public Map<String, Long> getGatewayDeviceMap() {
        return gatewayDeviceMap;
    }

    public void setGatewayDeviceMap(Map<String, Long> gatewayDeviceMap) {
        this.gatewayDeviceMap = gatewayDeviceMap;
    }
}

