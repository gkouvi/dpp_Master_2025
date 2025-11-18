package gr.uoi.dit.master2025.gkouvas.dpp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents an uploaded document belonging to a specific device.
 * Documents may include:
 * - Technical manuals
 * - Certificates
 * - Installation photos
 * - Service reports
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    /** Primary key of the document */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Original filename */
    private String filename;

    /** MIME type (PDF, JPG, PNG, etc.) */
    private String fileType;

    /** Timestamp of upload */
    private LocalDateTime uploadedAt;

    /**
     * The device this document belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    @JsonBackReference("device-documents")
    private Device device;

    /** Binary file contents */
    @Lob
    private byte[] data;
}
