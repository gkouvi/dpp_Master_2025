package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class MaintenanceBoxesKpiDto {
    private long total;
    private long completed;
    private long pending;
    private long overdue;
    private long cancel;
}
