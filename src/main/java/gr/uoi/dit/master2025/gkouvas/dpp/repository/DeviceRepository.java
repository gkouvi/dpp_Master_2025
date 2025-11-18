package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Device entity.
 *
 * Handles CRUD operations for devices.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {
    // Additional queries will be added later (e.g., findByBuildingId)
}
