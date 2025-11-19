package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.BuildingDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.DppSite;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.BuildingRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DppSiteRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.util.QRGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer responsible for building management.
 */
@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final DppSiteRepository siteRepository;

    public BuildingService(BuildingRepository buildingRepository,
                           DppSiteRepository siteRepository) {
        this.buildingRepository = buildingRepository;
        this.siteRepository = siteRepository;
    }

    public BuildingDto createBuilding(BuildingDto dto) {
        Building b = new Building();
        b.setName(dto.getName());
        b.setAddress(dto.getAddress());
        b.setSite(siteRepository.findById(dto.getSiteId()).orElseThrow());

        Building saved = buildingRepository.save(b);
        return toDto(saved);
    }

    public List<BuildingDto> getBuildings() {
        return buildingRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public BuildingDto getBuildingById(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Το κτίριο δεν βρέθηκε: " + id));
        return toDto(building);
    }

    public void deleteBuilding(Long id) {

        Building b = buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Το κτίριο δεν βρέθηκε: " + id));

        if (!b.getDevices().isEmpty()) {
            throw new RuntimeException("Δεν είναι δυνατή η διαγραφή κτιρίου με συσκευές.");
        }

        buildingRepository.deleteById(id);
    }


    private BuildingDto toDto(Building building) {
        BuildingDto dto = new BuildingDto();
        dto.setId(building.getId());
        dto.setName(building.getName());
        dto.setAddress(building.getAddress());
        dto.setSiteId(building.getSite() != null ? building.getSite().getId() : null);
        return dto;
    }

    public List<BuildingDto> getBuildingsBySite(Long siteId) {
        return buildingRepository.findBySiteId(siteId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public BuildingDto updateBuilding(Long id, BuildingDto dto) {

        Building b = buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Το κτίριο δεν βρέθηκε: " + id));

        b.setName(dto.getName());
        b.setAddress(dto.getAddress());

        if (dto.getSiteId() != null) {
            DppSite site = siteRepository.findById(dto.getSiteId())
                    .orElseThrow();
            b.setSite(site);
        }

        Building saved = buildingRepository.save(b);
        return toDto(saved);
    }


}
