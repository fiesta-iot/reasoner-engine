package eu.fiestaiot.reasoner.service.service.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by hungnguyendang on 27/07/2017.
 */
public class RuleCreationNonExpertRequest {

    @NotNull
    private String name;
    private String description;
    @NotNull
    private String quantityKind;
    @NotNull
    private String unitOfMeasurement;


    @NotNull
    private String sensor;

    private float latitude;

    private float longitude;

    private String sensorSampleData;
    private String sensorMeta;

    private String sensorEndp;

    private String hashedSensor;

    @NotNull
    List<RuleOperator> rules;

    public RuleCreationNonExpertRequest() {
    }

    public RuleCreationNonExpertRequest(String name, String description, String quantityKind, String unitOfMeasurement, String sensor, float latitude, float longitude, String sensorSampleData, String sensorMeta, String sensorEndp, List<RuleOperator> rules) {
        this.name = name;
        this.description = description;
        this.quantityKind = quantityKind;
        this.unitOfMeasurement = unitOfMeasurement;
        this.sensor = sensor;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sensorSampleData = sensorSampleData;
        this.sensorMeta = sensorMeta;
        this.sensorEndp = sensorEndp;
        this.rules = rules;
    }


    public String getHashedSensor() {
        return hashedSensor;
    }

    public void setHashedSensor(String hashedSensor) {
        this.hashedSensor = hashedSensor;
    }

    public String getSensorEndp() {
        return sensorEndp;
    }

    public void setSensorEndp(String sensorEndp) {
        this.sensorEndp = sensorEndp;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
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

    public String getSensorSampleData() {
        return sensorSampleData;
    }

    public void setSensorSampleData(String sensorSampleData) {
        this.sensorSampleData = sensorSampleData;
    }

    public String getSensorMeta() {
        return sensorMeta;
    }

    public void setSensorMeta(String sensorMeta) {
        this.sensorMeta = sensorMeta;
    }

    public List<RuleOperator> getRules() {
        return rules;
    }

    public void setRules(List<RuleOperator> rules) {
        this.rules = rules;
    }
}
