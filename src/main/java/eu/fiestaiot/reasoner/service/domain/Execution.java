package eu.fiestaiot.reasoner.service.domain;


import io.swagger.models.auth.In;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A Execution.
 */
@Entity
@Table(name = "execution")
public class Execution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "started")
    private Date started;

    @Column(name = "ended")
    private Date ended;

    @Column(name = "rule_content")
    private String ruleContent;

    @Column(name = "original_data")
    private String originalData;

    @Column(name = "inffered_data")
    private String infferedData;

    @Column(name = "full_data")
    private String fullData;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "sensor")
    private String sensor;

    private Integer executeType;


    @ManyToOne
    @NotNull
    private RegisterRule registerRule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public Execution status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getType() {
        return executeType;
    }

    public void setType(Integer type) {
        this.executeType = type;
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

    public Execution ruleContent(String ruleContent) {
        this.ruleContent = ruleContent;
        return this;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public String getOriginalData() {
        return originalData;
    }

    public Execution originalData(String originalData) {
        this.originalData = originalData;
        return this;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    public String getInfferedData() {
        return infferedData;
    }

    public Execution infferedData(String infferedData) {
        this.infferedData = infferedData;
        return this;
    }

    public void setInfferedData(String infferedData) {
        this.infferedData = infferedData;
    }

    public String getFullData() {
        return fullData;
    }

    public Execution fullData(String fullData) {
        this.fullData = fullData;
        return this;
    }

    public void setFullData(String fullData) {
        this.fullData = fullData;
    }

    public String getUserId() {
        return userId;
    }

    public Execution userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSensor() {
        return sensor;
    }

    public Execution sensor(String sensor) {
        this.sensor = sensor;
        return this;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public RegisterRule getRegisterRule() {
        return registerRule;
    }

    public Execution registerRule(RegisterRule registerRule) {
        this.registerRule = registerRule;
        return this;
    }

    public void setRegisterRule(RegisterRule registerRule) {
        this.registerRule = registerRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Execution execution = (Execution) o;
        if (execution.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, execution.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Execution{" +
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
            ", registerRule=" + registerRule +
            '}';
    }
}
