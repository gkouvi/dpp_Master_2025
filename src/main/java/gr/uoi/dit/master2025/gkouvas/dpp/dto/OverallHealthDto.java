package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import java.util.List;

public class OverallHealthDto {

    public long totalDevices;
    public long onlineCount;
    public long offlineCount;

    public List<DeviceHealthDto> devices;

    public double fleetUptimePercent;
}
