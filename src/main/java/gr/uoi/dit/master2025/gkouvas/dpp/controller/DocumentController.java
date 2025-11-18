package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.DocumentDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.DocumentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing device documents.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<DocumentDto>> getDocumentsByDevice(@PathVariable Long deviceId) {
        return ResponseEntity.ok(documentService.getDocumentsByDevice(deviceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> downloadDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.downloadDocument(id));
    }

    @PostMapping
    public ResponseEntity<DocumentDto> uploadDocument(@RequestBody DocumentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.uploadDocument(dto));
    }
}
