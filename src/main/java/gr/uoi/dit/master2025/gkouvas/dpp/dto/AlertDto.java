package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import gr.uoi.dit.master2025.gkouvas.dpp.util.AlertSeverity;
import gr.uoi.dit.master2025.gkouvas.dpp.util.AlertStatus;
import javafx.scene.control.Alert;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Αντικείμενο μεταφοράς δεδομένων για ειδοποίηση.
 * * Χρησιμοποιείται για την αποσύνδεση του επιπέδου API από την οντότητα JPA.
 */

@Data
public class AlertDto {

    private Long alertId;
    private Long deviceId;
    private String message;
    private LocalDateTime dueDate;
    private AlertStatus status;
    private String deviceName;
    private LocalDateTime createdAt;
    private AlertSeverity severity;


}