package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.BuildingDto;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.BuildingRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.service.BuildingService;
import org.springframework.http.*;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST controller for managing buildings inside military sites.
 */
@RestController
@RequestMapping("/buildings")
public class BuildingController {

    private final BuildingService buildingService;
    private final BuildingRepository buildingRepo;

    public BuildingController(BuildingService buildingService, BuildingRepository buildingRepo) {
        this.buildingService = buildingService;
        this.buildingRepo = buildingRepo;
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

    // ---------- QR UPLOAD ----------
    @PostMapping(value = "/{id}/qr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadQr(@PathVariable Long id,
                                         @RequestParam("file") MultipartFile file) throws Exception {

        Building b = buildingRepo.findById(id).orElseThrow();
        b.setQrCode(file.getBytes());
        buildingRepo.save(b);
        return ResponseEntity.ok().build();
    }

    // -------- QR RETRIEVE ----------
    @GetMapping(value = "/qr/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getQr(@PathVariable Long id) {
        return buildingRepo.findById(id)
                .map(Building::getQrCode)
                .orElse(null);
    }
    @GetMapping("/site/{siteId}")
    public List<BuildingDto> getBuildingsBySite(@PathVariable Long siteId) {
        return buildingService.getBuildingsBySite(siteId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildingDto> updateBuilding(
            @PathVariable Long id,
            @RequestBody BuildingDto dto) {

        BuildingDto updated = buildingService.updateBuilding(id, dto);
        return ResponseEntity.ok(updated);
    }


}
