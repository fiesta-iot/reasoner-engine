package eu.fiestaiot.reasoner.service.service.dto;

/**
 * Created by hungnguyendang on 17/08/2017.
 */
public class SensorData {

    private String sensingDevice;
    private String dateTime;
    private String instant;
    private Double dataValue;
    private String obsValue;
    private String sensorOutput;
    private String observation;


    public SensorData() {
    }

    public String getSensingDevice() {
        return sensingDevice;
    }

    public void setSensingDevice(String sensingDevice) {
        this.sensingDevice = sensingDevice;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Double getDataValue() {
        return dataValue;
    }

    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }

    public String getObsValue() {
        return obsValue;
    }

    public void setObsValue(String obsValue) {
        this.obsValue = obsValue;
    }

    public String getSensorOutput() {
        return sensorOutput;
    }

    public void setSensorOutput(String sensorOutput) {
        this.sensorOutput = sensorOutput;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getInstant() {
        return instant;
    }

    public void setInstant(String instant) {
        this.instant = instant;
    }


    @Override
    public String toString() {
        return "SensorData{" +
            "sensingDevice='" + sensingDevice + '\'' +
            ", dateTime='" + dateTime + '\'' +
            ", instant='" + instant + '\'' +
            ", dataValue=" + dataValue +
            ", obsValue='" + obsValue + '\'' +
            ", sensorOutput='" + sensorOutput + '\'' +
            ", observation='" + observation + '\'' +
            '}';
    }
}

