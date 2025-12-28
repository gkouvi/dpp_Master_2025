package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import java.time.LocalDate;

public class MaintenanceDailySummaryDTO {

    private LocalDate date;
    private long cancelled;
    private long completed;
    private long pending;


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCancelled(long cancelled) {
        this.cancelled = cancelled;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public void setPending(long pending) {
        this.pending = pending;
    }

    public MaintenanceDailySummaryDTO(
            LocalDate date,
            long canceleld,
            long completed,
            long pending) {
        this.date = date;
        this.cancelled = canceleld;
        this.completed = completed;
        this.pending = pending;

    }

    public LocalDate getDate() {
        return date;
    }

    public long getCancelled() {
        return cancelled;
    }

    public long getCompleted() {
        return completed;
    }

    public long getPending() {
        return pending;
    }


}
