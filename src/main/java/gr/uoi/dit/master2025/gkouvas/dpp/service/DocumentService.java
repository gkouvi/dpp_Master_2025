package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DocumentDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Document;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DeviceRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles uploading, downloading and listing documents for devices.
 */
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DeviceRepository deviceRepository;

    public DocumentService(DocumentRepository documentRepository,
                           DeviceRepository deviceRepository) {
        this.documentRepository = documentRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<DocumentDto> getDocumentsByDevice(Long deviceId) {
        return documentRepository.findByDevice_DeviceId(deviceId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public DocumentDto uploadDocument(DocumentDto dto) {
        Device device = deviceRepository.findById(dto.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found: " + dto.getDeviceId()));

        Document doc = new Document();
        doc.setDevice(device);
        doc.setFilename(dto.getFilename());
        doc.setFileType(dto.getFileType());
        doc.setUploadedAt(LocalDateTime.now());
        doc.setData(dto.getData());

        Document saved = documentRepository.save(doc);
        return toDto(saved);
    }

    public DocumentDto downloadDocument(Long id) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found: " + id));
        return toDto(doc);
    }

    private DocumentDto toDto(Document doc) {
        DocumentDto dto = new DocumentDto();
        dto.setId(doc.getId());
        dto.setFilename(doc.getFilename());
        dto.setFileType(doc.getFileType());
        dto.setUploadedAt(doc.getUploadedAt());
        dto.setDeviceId(doc.getDevice().getDeviceId());
        dto.setData(doc.getData());
        return dto;
    }
}

