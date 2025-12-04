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


}
