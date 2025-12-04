package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.MaintenanceStatsDto;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.MonthCount;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.MaintenanceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

