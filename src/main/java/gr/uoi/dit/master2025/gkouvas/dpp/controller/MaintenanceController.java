package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceLogDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.MaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing device maintenance logs.
 */
@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    /**
     * Returns all maintenance logs.
     *
     * @return list of MaintenanceLogDto
     */
    @GetMapping
    public ResponseEntity<List<MaintenanceLogDto>> getAllLogs() {
        return ResponseEntity.ok(maintenanceService.getAllLogs());
    }

    /**
     * Returns maintenance logs for a specific device.
     *
     * @param deviceId the device ID
     * @return list of MaintenanceLogDto
     */
    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<MaintenanceLogDto>> getLogsByDevice(@PathVariable Long deviceId) {
        return ResponseEntity.ok(maintenanceService.getLogsByDevice(deviceId));
    }

    /**
     * Creates a new maintenance log entry.
     *
     * @param dto maintenance data
     * @return created MaintenanceLogDto
     */
    @PostMapping
    public ResponseEntity<MaintenanceLogDto> createLog(@RequestBody MaintenanceLogDto dto) {
        MaintenanceLogDto created = maintenanceService.createLog(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
