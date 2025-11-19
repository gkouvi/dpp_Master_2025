package gr.uoi.dit.master2025.gkouvas.dpp.controller;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.SiteDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.DppSite;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DppSiteRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.service.SiteService;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST controller for managing military sites.
 */
@RestController
@RequestMapping("/sites")
public class SiteController {

    private final SiteService siteService;
    private final DppSiteRepository siteRepo;

    public SiteController(SiteService siteService, DppSiteRepository siteRepo) {
        this.siteService = siteService;
        this.siteRepo = siteRepo;
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

    @PostMapping(value = "/{id}/qr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadQr(@PathVariable Long id,
                                         @RequestParam("file") MultipartFile file) throws Exception {


        DppSite s = siteRepo.findById(id).orElseThrow();
        s.setQrCode(file.getBytes());
        siteRepo.save(s);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/qr/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getQr(@PathVariable Long id) {
        return siteRepo.findById(id)
                .map(DppSite::getQrCode)
                .orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteDto> updateSite(
            @PathVariable Long id,
            @RequestBody SiteDto dto) {

        SiteDto updated = siteService.updateSite(id, dto);
        return ResponseEntity.ok(updated);
    }



}

