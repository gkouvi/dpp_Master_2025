package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object for MaintenanceLog.
 * Represents maintenance record information for API clients.
 */
@Data
public class MaintenanceLogDto {

    private Long logId;
    private Long deviceId;
    private LocalDate maintenanceDate;
    private String description;
    private String technician;
}
