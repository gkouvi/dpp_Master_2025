package gr.uoi.dit.master2025.gkouvas.dpp.service;

import gr.uoi.dit.master2025.gkouvas.dpp.dto.FailureCell;
import gr.uoi.dit.master2025.gkouvas.dpp.dto.FailureHeatCell;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.AlertRepository;
import gr.uoi.dit.master2025.gkouvas.dpp.repository.UptimeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlertHeatmapService {

    @Autowired
    private UptimeLogRepository repo;

    public List<FailureHeatCell> getHeatmap() {

        List<Object[]> rows = repo.getHeatmapCountsRaw();
        List<FailureHeatCell> result = new ArrayList<>();

        for (Object[] r : rows) {

            int day = ((Number) r[0]).intValue()-1;
            int hour     = ((Number) r[1]).intValue();// 1 = Κυριακή
            long count   = ((Number) r[2]).longValue();

            //int day = (mysqlDay - 1); // shift to 0 = Κυριακή, 1 = Δευτέρα…
            List<String> devices = repo.getDevicesForCell(day, hour);

            result.add(new FailureHeatCell(day, hour, count, devices));
        }

        return result;
    }



}

