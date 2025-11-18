package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.DppSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository for managing DppSite entities.
 *
 * Enables CRUD operations and allows site lookup by name.
 */
@Repository
public interface DppSiteRepository extends JpaRepository<DppSite, Long> {

    /**
     * Finds a site by its name.
     *
     * @param siteName the name of the site
     * @return Optional containing the site if found
     */
    Optional<DppSite> findByName(String siteName);
}
