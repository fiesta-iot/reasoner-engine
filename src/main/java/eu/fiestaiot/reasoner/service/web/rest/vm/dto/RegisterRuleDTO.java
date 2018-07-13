package eu.fiestaiot.reasoner.service.web.rest.vm.dto;

import eu.fiestaiot.reasoner.service.domain.Reasoning;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class RegisterRuleDTO {

    private Long id;
    private String name;
    private String description;
    private String ruleContent;
    private String sensor;
    private float latitude;
    private float longitude;
    private String quantityKind;
    private String unitOfMeasurement;
    private String userId;

    private Date created;

    private Date updated;

    private Long ruleId;


    public RegisterRuleDTO() {
    }

    public RegisterRuleDTO(Long id, String name, String description, String ruleContent, String sensor, float latitude, float longitude, String quantityKind, String unitOfMeasurement, String userId, Date created, Date updated, Long ruleId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ruleContent = ruleContent;
        this.sensor = sensor;
        this.latitude = latitude;
        this.longitude = longitude;
        this.quantityKind = quantityKind;
        this.unitOfMeasurement = unitOfMeasurement;
        this.userId = userId;
        this.created = created;
        this.updated = updated;
        this.ruleId = ruleId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
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

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public String toString() {
        return "RegisterRuleDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", ruleContent='" + ruleContent + '\'' +
            ", sensor='" + sensor + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", quantityKind='" + quantityKind + '\'' +
            ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
            ", userId='" + userId + '\'' +
            ", created=" + created +
            ", updated=" + updated +
            ", ruleId=" + ruleId +
            '}';
    }
}
