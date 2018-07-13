package eu.fiestaiot.reasoner.service.domain;

import java.util.Date;

public class ReasoningSummary {
    private Date created;
    private Long total;

    public ReasoningSummary() {
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ReasoningSummary{" +
            "created=" + created +
            ", total=" + total +
            '}';
    }
}
