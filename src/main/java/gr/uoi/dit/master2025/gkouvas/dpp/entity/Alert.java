package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents an alert associated with a device.
 * Alerts include warnings, maintenance needs, faults, deadlines, etc.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    /** Primary key of the alert */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    /**
     * The device that generated or owns this alert.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    @JsonBackReference("device-alerts")
    private Device device;

    /** Short text message describing the alert */
    private String message;

    /** Optional due date (e.g., expiration, required action date) */
    private LocalDate dueDate;

    /** Alert status: Active / Resolved */
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
