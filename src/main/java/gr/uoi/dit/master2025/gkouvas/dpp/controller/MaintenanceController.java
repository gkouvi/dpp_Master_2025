package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceLogDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.MaintenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceService service;

    public MaintenanceController(MaintenanceService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<MaintenanceLogDto> getAll() {
        return service.getAllLogs();
    }

    @GetMapping("/device/{deviceId}")
    public List<MaintenanceLogDto> getDeviceMaintenance(@PathVariable Long deviceId) {
        return service.getMaintenanceForDevice(deviceId);
    }

    @GetMapping("/building/{buildingId}")
    public List<MaintenanceLogDto> getBuildingMaintenance(@PathVariable Long buildingId) {
        return service.getMaintenanceForBuilding(buildingId);
    }

    @PostMapping
    public MaintenanceLogDto createMaintenance(@RequestBody MaintenanceLogDto dto) {
        return service.createLog(dto);
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<MaintenanceLogDto> complete(@PathVariable Long id) {
        return ResponseEntity.ok(service.complete(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceLogDto> update(@PathVariable Long id,
                                                    @RequestBody MaintenanceLogDto dto) {
        dto.setLogId(id);
        return ResponseEntity.ok(service.update(dto));
    }
    @GetMapping("/upcoming-months")
    public ResponseEntity<Map<String, Long>> getUpcomingByMonth() {
        return ResponseEntity.ok(service.getUpcomingByMonth());
    }

}
