package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.FailureCell;
import gr.uoi.dit.master2025.gkouvas.dpp.service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/monitoring")
public class MonitoringController {

    private final MonitoringService service;

    public MonitoringController(MonitoringService service) {
        this.service = service;
    }

    @GetMapping("/heatmap")
    public ResponseEntity<List<FailureCell>> getHeatmap() {
        return ResponseEntity.ok(service.getFailureHeatmap());
    }
}

