package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceDailySummaryDTO;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


    List<MaintenanceLog> findByDevice_DeviceId(Long deviceId);
    List<MaintenanceLog> findByBuilding_Id(Long buildingId);
    List<MaintenanceLog> findAll();

    @Query(
            value = """

                    SELECT
                                       DATE_FORMAT(
                                           CASE
                                               WHEN m.maintenance_interval = 'DAILY' THEN DATE_ADD(m.maintenance_date, INTERVAL 1 DAY)
                                               WHEN m.maintenance_interval = 'MONTHLY' THEN DATE_ADD(m.maintenance_date, INTERVAL 1 MONTH)
                                               WHEN m.maintenance_interval = 'SEMI_ANNUAL' THEN DATE_ADD(m.maintenance_date, INTERVAL 6 MONTH)
                                               WHEN m.maintenance_interval = 'ANNUAL' THEN DATE_ADD(m.maintenance_date, INTERVAL 1 YEAR)
                                           END,
                                       '%Y-%m') AS month,
                                       COUNT(*) AS cnt
                                   FROM maintenance_log m
                                   WHERE m.maintenance_date IS NOT NULL
                                   GROUP BY month
                                   HAVING month IS NOT NULL
                                   ORDER BY month;
                                   
        """,
            nativeQuery = true
    )
    List<Object[]> getUpcomingByMonthNative();

    @Query("""
    SELECT new gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceDailySummaryDTO(
        m.plannedDate,
        SUM(CASE WHEN m.status = 'CANCELLED' THEN 1 ELSE 0 END),
        SUM(CASE WHEN m.status = 'COMPLETED' THEN 1 ELSE 0 END),
        SUM(CASE WHEN m.status = 'PENDING' THEN 1 ELSE 0 END)
       
    )
    FROM MaintenanceLog m
    WHERE m.device.deviceId = :deviceId
      AND m.plannedDate IS NOT NULL
    GROUP BY m.plannedDate
    ORDER BY m.plannedDate
""")
    List<MaintenanceDailySummaryDTO> findDailySummaryByDevice(
            @Param("deviceId") Long deviceId);

    @Query("""
    SELECT new gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceDailySummaryDTO(
        m.plannedDate,
        SUM(CASE WHEN m.status = 'CANCELLED' THEN 1 ELSE 0 END),
        SUM(CASE WHEN m.status = 'COMPLETED' THEN 1 ELSE 0 END),
        SUM(CASE WHEN m.status = 'PENDING' THEN 1 ELSE 0 END)
    )
    FROM MaintenanceLog m
    WHERE m.plannedDate IS NOT NULL
    GROUP BY m.plannedDate
    ORDER BY m.plannedDate
""")
    List<MaintenanceDailySummaryDTO> findGlobalDailySummary();




}

