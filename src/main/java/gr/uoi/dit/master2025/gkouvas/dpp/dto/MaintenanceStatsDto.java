package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MaintenanceStatsDto {
    private List<MonthCount> planned;
    private List<MonthCount> completed;
}


