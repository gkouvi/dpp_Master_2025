package gr.uoi.dit.master2025.gkouvas.dpp.dto;

import java.util.List;

public class FailureHeatCell {

    private int hour;
    private int day;
    private long count;
    private List<String> devices;

    public FailureHeatCell(int day, int hour, long count, List<String> devices) {
        this.hour = hour;
        this.day = day;
        this.count = count;
        this.devices = devices;
    }

    public FailureHeatCell() {
    }

    public int getHour() { return hour; }
    public int getDay() { return day; }
    public long getCount() { return count; }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public List<String> getDevices() { return devices; }
}
