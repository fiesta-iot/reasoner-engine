package eu.fiestaiot.reasoner.service.domain;

import java.util.Date;

public class RegisterRuleSummary {
    private Date created;
    private Long total;

    public RegisterRuleSummary() {
    }

    public RegisterRuleSummary(Date created, Long total) {
        this.created = created;
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "RegisterRuleSummary{" +
            "total=" + total +
            ", created=" + created +
            '}';
    }
}
