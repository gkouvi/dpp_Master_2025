/*package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;*/

/*
 * Represents a maintenance action performed on a device.
 * Includes routine checks, repairs, firmware upgrades, inspections.
 */
/*
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLog {

        */
/* Primary key of the maintenance entry *//*

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long logId;

        */
/*
         * Device linked to this maintenance record.
         *//*

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "device_id", nullable = false)
        @JsonBackReference("device-maintenance")
        private Device device;

        */
/*Date when maintenance was performed *//*

        private LocalDate maintenanceDate;

        */
/* Detailed description of the task performed *//*

        private String description;

        */
/* Full name or code of the technician who performed the work *//*

        private String technician;
}
*///22112025

package gr.uoi.dit.master2025.gkouvas.dpp.entity;

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
        private Device device;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "building_id")
        private Building building;

        private LocalDate maintenanceDate;

        private String description;

        private String technician;
}
