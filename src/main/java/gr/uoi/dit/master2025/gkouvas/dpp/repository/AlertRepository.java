package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for managing Alert entities.
 *
 * Provides CRUD operations for alerts and custom methods for retrieving
 * alerts associated with a specific device.
 */
public interface AlertRepository extends JpaRepository<Alert, Long> {

    /**
     * Finds all alerts belonging to a specific device.
     *
     * @param id the device ID
     * @return list of alerts for the device
     */
    List<Alert> findByDevice_DeviceId(Long id);
}
