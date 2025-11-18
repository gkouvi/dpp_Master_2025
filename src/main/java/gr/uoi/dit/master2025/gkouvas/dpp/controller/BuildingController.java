package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.BuildingDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.BuildingService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing buildings inside military sites.
 */
@RestController
@RequestMapping("/buildings")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping
    public ResponseEntity<List<BuildingDto>> getAllBuildings() {
        return ResponseEntity.ok(buildingService.getBuildings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingDto> getBuilding(@PathVariable Long id) {
        return ResponseEntity.ok(buildingService.getBuildingById(id));
    }

    @PostMapping
    public ResponseEntity<BuildingDto> createBuilding(@RequestBody BuildingDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildingService.createBuilding(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }
}

