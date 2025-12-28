package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceInterval;
import gr.uoi.dit.master2025.gkouvas.dpp.util.DeviceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"building", "alerts", "documents", "maintenanceLogs"})
@EqualsAndHashCode(exclude = {"building", "alerts", "documents", "maintenanceLogs"})
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "device_maintenance_intervals",
            joinColumns = @JoinColumn(name = "device_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "interval_type")     // ✔ εδώ η αλλαγή
    private List<MaintenanceInterval> maintenanceIntervals = new ArrayList<>();



    private String ipAddress;

    private LocalDate lastMaintenanceDate = LocalDate.now();


    // NEW FIELD – used for ping monitoring
    //private boolean offline = false;
    @Column
    private Boolean offline = false;

    @Column
    private LocalDateTime lastCheck;



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

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] qrCode;

    @Column(name = "bim_element_id", length = 64)
    private String bimElementId;   // IFC GlobalId (συνήθως 22 chars), κρατάμε 64 για άνεση

    @Column(name = "bim_discipline", length = 50)
    private String bimDiscipline;  // HVAC, Electrical, Security, etc.

}
