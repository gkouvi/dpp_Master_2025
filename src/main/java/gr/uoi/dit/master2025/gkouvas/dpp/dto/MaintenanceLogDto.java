
package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.Data;
import gr.uoi.dit.master2025.gkouvas.dpp.util.MaintenanceStatus;
import java.time.LocalDate;

/**
 * DTO για μεταφορά δεδομένων MaintenanceLog από/προς REST.
 */
@Data
public class MaintenanceLogDto {

    private Long logId;

    /** Optional: αν η εγγραφή αφορά συγκεκριμένη συσκευή */
    private Long deviceId;

    /** Optional: αν η εγγραφή αφορά ολόκληρο κτήριο */
    private LocalDate plannedDate;
    private LocalDate performedDate;
    private Long buildingId;
    private MaintenanceInterval interval;
    private MaintenanceStatus status;
    private LocalDate maintenanceDate;
    private String description;
    private String technician;

    /*public MaintenanceLogDto(Long logId, Long deviceId, Long buildingId,
                             String description, String technician, LocalDate date) {
        this.logId = logId;
        this.deviceId = deviceId;
        this.buildingId = buildingId;
        this.description = description;
        this.technician = technician;
        this.maintenanceDate = date;
    }*/
    public MaintenanceLogDto(){}

}


