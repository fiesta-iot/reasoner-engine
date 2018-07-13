package eu.fiestaiot.reasoner.service.service.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by hungnguyendang on 20/07/2017.
 */
public class ValidateRuleRequest {

    @NotNull
    private String sensorId;
    @NotNull
    private String rule;

    public ValidateRuleRequest() {
    }


    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "ValidateRuleRequest{" +
            "sensorId='" + sensorId + '\'' +
            ", rule='" + rule + '\'' +
            '}';
    }
}
