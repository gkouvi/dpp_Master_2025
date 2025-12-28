package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceBoxesKpiDto;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceStatsDto;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.MonthCount;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.MaintenanceLogRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.util.MaintenanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class MaintenanceStatsService {

    private final MaintenanceLogRepository repo;

    public MaintenanceStatsService(MaintenanceLogRepository repo) {
        this.repo = repo;
    }

    public MaintenanceStatsDto getStats() {
        List<MonthCount> planned = repo.getPlannedMonthlyCountsRaw()
                .stream()
                .map(row -> new MonthCount(
                        (String) row[0],
                        ((Number) row[1]).longValue()))
                .toList();

        List<MonthCount> completed = repo.getCompletedMonthlyCountsRaw()
                .stream()
                .map(row -> new MonthCount(
                        (String) row[0],
                        ((Number) row[1]).longValue()))
                .toList();

        return new MaintenanceStatsDto(planned, completed);
    }

    public MaintenanceBoxesKpiDto getKpis() {

        LocalDate today = LocalDate.now();

        long total = repo.count();

        long completed = repo.countBystatus(MaintenanceStatus.COMPLETED);

        long overdue = repo.countOverdue(today);
        // status = PENDING AND planned_date < today

        long pending = repo.countPending(today);
        // status = PENDING AND planned_date >= today
        long cancel = repo.countCancelled();

        return new MaintenanceBoxesKpiDto(total, completed, pending, overdue,cancel);
    }

}

