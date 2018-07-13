package eu.fiestaiot.reasoner.service.web.rest.vm.request;

import eu.fiestaiot.reasoner.service.domain.Execution;
import eu.fiestaiot.reasoner.service.domain.RegisterRule;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ExecuteRuleRequest {

    private Date started;
    private Date ended;
    @NotNull
    private Integer executeType;
    @NotNull
    private Long registerRuleId;
    public ExecuteRuleRequest() {
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getEnded() {
        return ended;
    }

    public void setEnded(Date ended) {
        this.ended = ended;
    }

    public Integer getExecuteType() {
        return executeType;
    }

    public void setExecuteType(Integer executeType) {
        this.executeType = executeType;
    }

    public Long getRegisterRuleId() {
        return registerRuleId;
    }

    public void setRegisterRuleId(Long registerRuleId) {
        this.registerRuleId = registerRuleId;
    }

    @Override
    public String toString() {
        return "ExecuteRuleRequest{" +
            "started=" + started +
            ", ended=" + ended +
            ", executeType='" + executeType + '\'' +
            ", registerRuleId=" + registerRuleId +
            '}';
    }
}
