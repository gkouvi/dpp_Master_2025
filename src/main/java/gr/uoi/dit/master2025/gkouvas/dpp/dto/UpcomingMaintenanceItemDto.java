package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UpcomingMaintenanceItemDto {

    private Long deviceId;
    private String deviceName;

    private MaintenanceInterval interval;
    private LocalDate nextMaintenanceDate;

    // getters / setters

    public UpcomingMaintenanceItemDto(Long deviceId, String deviceName,
                                      MaintenanceInterval interval,
                                      LocalDate nextMaintenanceDate) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.interval = interval;
        this.nextMaintenanceDate = nextMaintenanceDate;
    }
}

