package eu.fiestaiot.reasoner.service.web.rest.vm.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReasoningDTO {


    private Long id;

    private String name;

    private String userId;

    private Date created;

    private Date updated;

    private String content;

    private String sensor;

    private String description;

    private float latitude = 0;

    private float longitude = 0;

    private String quantityKind;

    private String unitOfMeasurement;

    private  int ruleType;

    private String nonExpertOriginalRules;

    public ReasoningDTO() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getQuantityKind() {
        return quantityKind;
    }

    public void setQuantityKind(String quantityKind) {
        this.quantityKind = quantityKind;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public String getNonExpertOriginalRules() {
        return nonExpertOriginalRules;
    }

    public void setNonExpertOriginalRules(String nonExpertOriginalRules) {
        this.nonExpertOriginalRules = nonExpertOriginalRules;
    }

    @Override
    public String toString() {
        return "ReasoningDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", userId='" + userId + '\'' +
            ", created=" + created +
            ", updated=" + updated +
            ", content='" + content + '\'' +
            ", sensor='" + sensor + '\'' +
            ", description='" + description + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", quantityKind='" + quantityKind + '\'' +
            ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
            ", ruleType=" + ruleType +
            ", nonExpertOriginalRules='" + nonExpertOriginalRules + '\'' +
            '}';
    }
}
