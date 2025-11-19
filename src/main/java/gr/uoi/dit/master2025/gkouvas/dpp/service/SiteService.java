package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.SiteDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.DppSite;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DppSiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing Site-related business logic.
 */
@Service
public class SiteService {

    private final DppSiteRepository siteRepository;

    public SiteService(DppSiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    /**
     * Retrieves all sites.
     */
    public List<SiteDto> getAllSites() {
        return siteRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves site by ID.
     */
    public SiteDto getSiteById(Long id) {
        DppSite site = siteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Η Μονάδα δε βρέθηκε με id: " + id));
        return toDto(site);
    }

    /**
     * Creates a new site.
     */
    public SiteDto createSite(SiteDto dto) {
        DppSite site = new DppSite();
        site.setName(dto.getName());
        site.setRegion(dto.getRegion());
        site.setCoordinates(dto.getCoordinates());

        DppSite saved = siteRepository.save(site);
        return toDto(saved);
    }

    /**
     * Deletes a site.
     */
    public void deleteSite(Long id) {

        DppSite s = siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Η Μονάδα δε βρέθηκε: " + id));

        if (!s.getBuildings().isEmpty()) {
            throw new RuntimeException("Δεν είναι δυνατή η διαγραφή τοποθεσίας με κτίρια.");
        }

        siteRepository.deleteById(id);
    }


    /**
     * Converts entity → DTO.
     */
    private SiteDto toDto(DppSite site) {
        SiteDto dto = new SiteDto();
        dto.setId(site.getId());
        dto.setName(site.getName());
        dto.setRegion(site.getRegion());
        dto.setCoordinates(site.getCoordinates());
/*
        if (site.getBuildings() != null)
            dto.setBuildingIds(site.getBuildings()
                    .stream()
                    .map(Building::getId)
                    .collect(Collectors.toList()));*/

        return dto;
    }

    public SiteDto updateSite(Long id, SiteDto dto) {

        DppSite s = siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Η Μονάδα δε βρέθηκε: " + id));

        s.setName(dto.getName());
        s.setRegion(dto.getRegion());
        s.setCoordinates(dto.getCoordinates());

        DppSite saved = siteRepository.save(s);
        return toDto(saved);
    }



}

