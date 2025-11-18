package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.SiteDto;
import gr.uoi.dit.master2025.gkouvas.dpp.service.SiteService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing military sites.
 */
@RestController
@RequestMapping("/sites")
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping
    public ResponseEntity<List<SiteDto>> getAllSites() {
        return ResponseEntity.ok(siteService.getAllSites());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteDto> getSite(@PathVariable Long id) {
        return ResponseEntity.ok(siteService.getSiteById(id));
    }

    @PostMapping
    public ResponseEntity<SiteDto> createSite(@RequestBody SiteDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.createSite(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
        return ResponseEntity.noContent().build();
    }
}

