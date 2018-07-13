package eu.fiestaiot.reasoner.service.web.rest.vm.request;

import javax.validation.constraints.NotNull;

public class RegisterRuleRequest {

    private Long id;
    @NotNull
    private String name;
    private String description;

    @NotNull
    private String sensor;

    private String quantityKind;
    private String unitOfMeasurement;
    private float latitude = 0;
    private float longitude = 0;


    @NotNull
    private Long ruleId;


    public RegisterRuleRequest() {
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

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
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

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public String toString() {
        return "RegisterRuleRequest{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", sensor='" + sensor + '\'' +
            ", quantityKind='" + quantityKind + '\'' +
            ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", ruleId=" + ruleId +
            '}';
    }
}
