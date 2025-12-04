package gr.uoi.dit.master2025.gkouvas.dpp.dto;

public class FailureCell {
    public int hour;
    public int day;
    public long count;

    public FailureCell(int hour, int day, long count) {
        this.hour = hour;
        this.day = day;
        this.count = count;
    }
}
