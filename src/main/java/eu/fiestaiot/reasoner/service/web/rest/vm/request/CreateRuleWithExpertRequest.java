package eu.fiestaiot.reasoner.service.web.rest.vm.request;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;


public class CreateRuleWithExpertRequest {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String content;
    private String description;
    @NotNull
    private String sensor;
    @NotNull
    private String quantityKind;
    @NotNull
    private String unitOfMeasurement;

    private float latitude = 0;

    private float longitude = 0;


    public CreateRuleWithExpertRequest() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "CreateRuleWithExpertRequest{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", content='" + content + '\'' +
            ", description='" + description + '\'' +
            ", sensor='" + sensor + '\'' +
            ", quantityKind='" + quantityKind + '\'' +
            ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }
}
