package eu.fiestaiot.reasoner.service.web.rest.vm;

public class ReasoningStatisticResponse {
    private ReasoningStatistic created;
    private ReasoningStatistic used;

    public ReasoningStatisticResponse() {
    }

    public ReasoningStatistic getCreated() {
        return created;
    }

    public void setCreated(ReasoningStatistic created) {
        this.created = created;
    }

    public ReasoningStatistic getUsed() {
        return used;
    }

    public void setUsed(ReasoningStatistic used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "ReasoningStatisticResponse{" +
            "created=" + created +
            ", used=" + used +
            '}';
    }
}
