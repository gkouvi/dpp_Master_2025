package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a device installed inside a building
 * (e.g., CCTV camera, alarm sensor, access control reader).
 *
 * Each device belongs to one Building and can have:
 * - Alerts
 * - Documents
 * - Maintenance logs
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    /** Primary key of the device */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;

    /** Device display name */
    private String name;

    /** Type of device: Camera, Alarm, Access Control, etc. */
    private String type;

    /** Manufacturer serial number */
    private String serialNumber;

    /** Date the device was installed */
    private LocalDate installationDate;

    /** Current firmware version (useful for lifecycle management) */
    private String firmwareVersion;

    /** Online/Offline/Inactive */
    private String status;

    /**
     * Many devices belong to one building.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    @JsonBackReference("building-devices")
    private Building building;

    /**
     * Device alerts (e.g., maintenance, fault, expiration)
     */
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("device-alerts")
    private List<Alert> alerts;

    /**
     * Documents related to the device (manuals, certificates, checklists, logs)
     */
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("device-documents")
    private List<Document> documents;

    /**
     * Maintenance history for this device
     */
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("device-maintenance")
    private List<MaintenanceLog> maintenanceLogs;
}
