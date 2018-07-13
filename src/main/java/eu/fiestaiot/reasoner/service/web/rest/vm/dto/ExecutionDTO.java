package eu.fiestaiot.reasoner.service.web.rest.vm.dto;

import eu.fiestaiot.reasoner.service.domain.RegisterRule;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ExecutionDTO {

    private Long id;


    private Boolean status;


    private Date created;


    private Date updated;


    private Date started;


    private Date ended;


    private String ruleContent;


    private String originalData;


    private String infferedData;


    private String fullData;


    private String userId;


    private String sensor;

    private Integer executeType;


    private Long registerRuleId;

    public ExecutionDTO() {
    }

    public ExecutionDTO(Long id, Boolean status, Date created, Date updated, Date started, Date ended, String ruleContent, String originalData, String infferedData, String fullData, String userId, String sensor, Integer executeType, Long registerRuleId) {
        this.id = id;
        this.status = status;
        this.created = created;
        this.updated = updated;
        this.started = started;
        this.ended = ended;
        this.ruleContent = ruleContent;
        this.originalData = originalData;
        this.infferedData = infferedData;
        this.fullData = fullData;
        this.userId = userId;
        this.sensor = sensor;
        this.executeType = executeType;
        this.registerRuleId = registerRuleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    public String getInfferedData() {
        return infferedData;
    }

    public void setInfferedData(String infferedData) {
        this.infferedData = infferedData;
    }

    public String getFullData() {
        return fullData;
    }

    public void setFullData(String fullData) {
        this.fullData = fullData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
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
        return "ExecutionDTO{" +
            "id=" + id +
            ", status=" + status +
            ", created=" + created +
            ", updated=" + updated +
            ", started=" + started +
            ", ended=" + ended +
            ", ruleContent='" + ruleContent + '\'' +
            ", originalData='" + originalData + '\'' +
            ", infferedData='" + infferedData + '\'' +
            ", fullData='" + fullData + '\'' +
            ", userId='" + userId + '\'' +
            ", sensor='" + sensor + '\'' +
            ", executeType=" + executeType +
            ", registerRuleId=" + registerRuleId +
            '}';
    }
}
