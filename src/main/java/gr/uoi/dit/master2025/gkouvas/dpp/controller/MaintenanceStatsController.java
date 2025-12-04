package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceStatsDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.MaintenanceStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maintenance/stats")
public class MaintenanceStatsController {

    private final MaintenanceStatsService service;

    public MaintenanceStatsController(MaintenanceStatsService service) {
        this.service = service;
    }

    @GetMapping("/monthly")
    public ResponseEntity<MaintenanceStatsDto> getMonthlyStats() {
        return ResponseEntity.ok(service.getStats());
    }
}
