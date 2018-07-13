package eu.fiestaiot.reasoner.service.service.dto;

/**
 * Created by hungnguyendang on 18/07/2017.
 */
public class Sensor {
    private String endp;
    private String unit;
    private String qk;
    private String sensor;
    private String hashedSensor;
    private String displaySensor;
    private String type;
    private String shortQk;
    private String shortUnit;
    private float  lng;
    private float lat;
    private String sensorData;

    public Sensor(String endp, String unit, String qk, String shortQk, String shortUnit,  String sensor, String hashedSensor, String displaySensor, String type, float lng, float lat) {
        this.endp = endp;
        this.unit = unit;
        this.qk = qk;
        this.sensor = sensor;
        this.hashedSensor = hashedSensor;
        this.displaySensor = displaySensor;
        this.type = type;
        this.lng = lng;
        this.lat = lat;
        this.shortQk = shortQk;
        this.shortUnit = shortUnit;
    }

    public Sensor() {
    }

    public String getSensorData() {
        return sensorData;
    }

    public void setSensorData(String sensorData) {
        this.sensorData = sensorData;
    }

    public String getShortQk() {
        return shortQk;
    }

    public void setShortQk(String shortQk) {
        this.shortQk = shortQk;
    }

    public String getShortUnit() {
        return shortUnit;
    }

    public void setShortUnit(String shortUnit) {
        this.shortUnit = shortUnit;
    }

    public String getDisplaySensor() {
        return displaySensor;
    }

    public void setDisplaySensor(String displaySensor) {
        this.displaySensor = displaySensor;
    }

    public String getHashedSensor() {
        return hashedSensor;
    }

    public void setHashedSensor(String hashedSensor) {
        this.hashedSensor = hashedSensor;
    }

    public String getEndp() {
        return endp;
    }

    public void setEndp(String endp) {
        this.endp = endp;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQk() {
        return qk;
    }

    public void setQk(String qk) {
        this.qk = qk;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }
}
