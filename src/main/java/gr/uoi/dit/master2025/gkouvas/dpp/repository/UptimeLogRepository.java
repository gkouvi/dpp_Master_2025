package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.UptimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface UptimeLogRepository extends JpaRepository<UptimeLog, Long> {

    @Query("SELECT u FROM UptimeLog u WHERE u.device.deviceId = :deviceId " +
            "AND u.timestamp >= :from ORDER BY u.timestamp ASC")
    List<UptimeLog> getLogsForDevice(Long deviceId, LocalDateTime from);

    @Query(
            value = """
          SELECT 
              HOUR(timestamp) AS hour,
              DAYOFWEEK(timestamp) AS dow,
              COUNT(*) AS fails
          FROM uptime_log
          WHERE online = false
          GROUP BY dow, hour
          ORDER BY dow, hour
      """,
            nativeQuery = true
    )
    List<Object[]> getFailureHeatmap();

    @Query("""
    SELECT 
        FUNCTION('DAYOFWEEK', a.createdAt) AS day,
        FUNCTION('HOUR', a.createdAt) AS hour,
        COUNT(a) AS cnt
    FROM Alert a
    GROUP BY 
        FUNCTION('DAYOFWEEK', a.createdAt),
        FUNCTION('HOUR', a.createdAt)
    ORDER BY 
        FUNCTION('DAYOFWEEK', a.createdAt),
        FUNCTION('HOUR', a.createdAt)
""")
    List<Object[]> getHeatmapCountsRaw();




    @Query("""
    SELECT DISTINCT 
        d.name
    FROM Alert a
    JOIN a.device d
    WHERE 
        (FUNCTION('DAYOFWEEK', a.createdAt)) = :day
        AND FUNCTION('HOUR', a.createdAt) = :hour
""")
    List<String> getDevicesForCell(int day, int hour);






}
