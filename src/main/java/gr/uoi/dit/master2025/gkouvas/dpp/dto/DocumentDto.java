package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Document.
 * Does NOT include file data unless explicitly requested.
 */
@Data
public class DocumentDto {

    private Long id;
    private Long deviceId;
    private String filename;
    private String fileType;
    private LocalDateTime uploadedAt;

    /** Optional: Include document binary data only for downloads */
    private byte[] data;
}
