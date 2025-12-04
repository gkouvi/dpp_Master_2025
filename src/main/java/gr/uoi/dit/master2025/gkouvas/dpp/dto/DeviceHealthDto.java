package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import java.time.LocalDateTime;

public class DeviceHealthDto {

    public Long deviceId;
    public String name;

    public boolean online;
    public double uptimePercent;

    public LocalDateTime lastCheck;
}

