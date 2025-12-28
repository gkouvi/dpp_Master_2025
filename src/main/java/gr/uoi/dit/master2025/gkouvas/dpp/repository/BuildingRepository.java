package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.Building;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.DppSite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Building entities.
 *
 * Provides methods for CRUD operations and custom query
 * for retrieving a building based on its name and the site it belongs to.
 */
public interface BuildingRepository extends JpaRepository<Building, Long> {

    /**
     * Finds a building by name inside a specific DppSite.
     *
     * Useful to prevent duplicates or locate buildings when importing data.
     *
     * @param buildingName the name of the building
     * @param site the parent site
     * @return Optional containing the building if found
     */
    Optional<Building> findByNameAndSite(String buildingName, DppSite site);
    List<Building> findBySiteId(Long siteId);


}
