package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceInterval;
import gr.uoi.dit.master2025.gkouvas.dpp.util.MaintenanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity που αναπαριστά μια εγγραφή συντήρησης.
 * Μπορεί να συνδέεται είτε με Device, είτε με Building, είτε και με τα δύο.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLog {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long logId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "device_id")
        //private Long deviceId;
        private Device device;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "building_id")
        private Building building;

        private LocalDate maintenanceDate;

        private String description;

        private String technician;


        private LocalDate plannedDate;    // πότε ΕΠΡΕΠΕ να γίνει
        private LocalDate performedDate;  // πότε ΕΓΙΝΕ πραγματικά

        @Enumerated(EnumType.STRING)
        @Column(name = "maintenance_interval")
        private MaintenanceInterval interval;

        @Enumerated(EnumType.STRING)
        @Column(name = "maintenance_status")
        private MaintenanceStatus status;   // ΝΕΟ






}
