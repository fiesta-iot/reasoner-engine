package eu.fiestaiot.reasoner.service.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * Created by hungnguyendang on 27/07/2017.
 */
public class ReExecuteRule implements Serializable {

    @NotNull
    private Long id;
    private Instant startTime;
    private Instant endTime;

    public ReExecuteRule(Long id) {
        this.id = id;
    }

    public ReExecuteRule() {
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReExecuteRule{" +
            "id=" + id +
            '}';
    }
}
