package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import lombok.Data;

@Data
public class MaintenanceKpiDto {
    private long critical;   // ≤3 days
    private long urgent;     // ≤7 days
    private long overdue;    // < today
    private long thisMonth;  // same month
}

