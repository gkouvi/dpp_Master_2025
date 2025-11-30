package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for MaintenanceLog entity.
 *
 * Provides CRUD operations and allows retrieving maintenance logs
 * associated with a specific device.
 */
public interface MaintenanceRepository extends JpaRepository<MaintenanceLog, Long> {

    /**
     * Finds maintenance logs that belong to a specific device.
     *
     * @param deviceId the device ID
     * @return list of maintenance logs for the device
     */
   /* List<MaintenanceLog> findByDevice_DeviceId(Long deviceId);*/

    List<MaintenanceLog> findByDevice_DeviceId(Long deviceId);
    List<MaintenanceLog> findByBuilding_Id(Long buildingId);
    List<MaintenanceLog> findAll();

}

