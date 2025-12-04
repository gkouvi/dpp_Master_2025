package gr.uoi.dit.master2025.gkouvas.dpp.dto;

public class MonthCount {

    private String month;
    private Long count;

    public MonthCount(String month, Long count) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "MonthCount{month='" + month + "', count=" + count + "}";
    }
}
