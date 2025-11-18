package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.AlertDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.AlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing alerts related to devices.
 */
@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * Returns all alerts in the system.
     *
     * @return list of AlertDto
     */
    @GetMapping
    public ResponseEntity<List<AlertDto>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }

    /**
     * Returns alerts for a specific device.
     *
     * @param deviceId the device ID
     * @return list of AlertDto
     */
    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<AlertDto>> getAlertsByDevice(@PathVariable Long deviceId) {
        return ResponseEntity.ok(alertService.getAlertsByDevice(deviceId));
    }

    /**
     * Creates a new alert.
     *
     * @param dto alert data
     * @return created AlertDto
     */
    @PostMapping
    public ResponseEntity<AlertDto> createAlert(@RequestBody AlertDto dto) {
        AlertDto created = alertService.createAlert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
