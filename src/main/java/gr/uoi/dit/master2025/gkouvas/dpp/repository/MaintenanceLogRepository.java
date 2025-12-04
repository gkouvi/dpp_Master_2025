package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MonthCount;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {

    @Query(value = """
        SELECT DATE_FORMAT(
                   CASE 
                       WHEN performed_date IS NOT NULL THEN performed_date
                       ELSE maintenance_date
                   END,'%Y-%m'
               ) AS month,
               COUNT(*) AS count
        FROM maintenance_log
        WHERE performed_date IS NOT NULL OR maintenance_date IS NOT NULL
        GROUP BY month
        ORDER BY month;
        """,
            nativeQuery = true)
    List<Object[]> getCompletedMonthlyCountsRaw();


    @Query(value = """
        SELECT DATE_FORMAT(maintenance_date, '%Y-%m') AS month,
               COUNT(*) AS count
        FROM maintenance_log
        WHERE maintenance_date IS NOT NULL
        GROUP BY month
        ORDER BY month;
        """,
            nativeQuery = true)
    List<Object[]> getPlannedMonthlyCountsRaw();
}
