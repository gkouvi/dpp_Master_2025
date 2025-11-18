package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.BuildingDto;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.DppSite;
import gr.uoi.dit.master2025.gkouvas.dpp.exception.ResourceNotFoundException;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.BuildingRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.DppSiteRepository;
import org.springframework.stereotype.Service;

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

    public List<BuildingDto> getBuildings() {
        return buildingRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BuildingDto getBuildingById(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found: " + id));
        return toDto(building);
    }

    public BuildingDto createBuilding(BuildingDto dto) {
        DppSite site = siteRepository.findById(dto.getSiteId())
                .orElseThrow(() -> new ResourceNotFoundException("Site not found: " + dto.getSiteId()));

        Building building = new Building();
        building.setName(dto.getName());
        building.setAddress(dto.getAddress());
        building.setSite(site);

        Building saved = buildingRepository.save(building);
        return toDto(saved);
    }

    public void deleteBuilding(Long id) {
        if (!buildingRepository.existsById(id))
            throw new ResourceNotFoundException("Building not found: " + id);

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
}

