package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object for Alert.
 * Used to decouple API layer from JPA entity.
 */
@Data
public class AlertDto {

    private Long alertId;
    private Long deviceId;
    private String message;
    private LocalDate dueDate;
    private String status;
}
