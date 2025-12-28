package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MonthCount;
import gr.uoi.dit.master2025.gkouvas.dpp.entity.MaintenanceLog;
import gr.uoi.dit.master2025.gkouvas.dpp.util.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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


    long countBystatus(MaintenanceStatus status);

    @Query("""
SELECT COUNT(m)
FROM MaintenanceLog m
WHERE m.status = 'PENDING'
AND m.plannedDate < :today
""")
    long countOverdue(LocalDate today);


    @Query("""
SELECT COUNT(m)
FROM MaintenanceLog m
WHERE m.status = 'PENDING'
AND m.plannedDate >= :today
""")
    long countPending(LocalDate today);


    @Query("""
SELECT COUNT(m)
FROM MaintenanceLog m
WHERE m.status = 'CANCELLED'
""")
    long countCancelled();
}



