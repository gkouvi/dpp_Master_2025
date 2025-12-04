package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.OverallHealthDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices/health")
public class DeviceHealthController {

    @Autowired
    private HealthService healthService;

    @GetMapping
    public ResponseEntity<OverallHealthDto> getHealth() {
        return ResponseEntity.ok(healthService.getFleetHealth());
    }
}
