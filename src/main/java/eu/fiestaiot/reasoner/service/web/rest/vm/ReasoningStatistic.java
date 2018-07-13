package eu.fiestaiot.reasoner.service.web.rest.vm;

import java.util.List;

public class ReasoningStatistic {
    private List<String> day;
    private List<Long> data;

    public ReasoningStatistic() {
    }

    public List<String> getDay() {
        return day;
    }

    public void setDay(List<String> day) {
        this.day = day;
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReasoningStatistic{" +
            "day=" + day +
            ", data=" + data +
            '}';
    }
}
