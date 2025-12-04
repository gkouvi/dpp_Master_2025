package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.FailureCell;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.UptimeLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringService {

    private final UptimeLogRepository repo;

    public MonitoringService(UptimeLogRepository repo) {
        this.repo = repo;
    }

    public List<FailureCell> getFailureHeatmap() {
        return repo.getFailureHeatmap()
                .stream()
                .map(r -> new FailureCell(
                        ((Number) r[0]).intValue(),
                        ((Number) r[1]).intValue(),
                        ((Number) r[2]).longValue()
                ))
                .toList();
    }
}

