package eu.fiestaiot.reasoner.service.domain;




import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A Reasoning.
 */
@Entity
@Table(name = "reasoning")
public class Reasoning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @NotNull
    @Column(name = "content", columnDefinition = "mediumtext")
    private String content;

    @NotNull
    @Column(name = "sensor", nullable = false,columnDefinition = "varchar(300)")
    private String sensor;

    private String hashedSensor;


    @Column(name = "sensor_endp",columnDefinition = "varchar(300)")
    private String sensorEndp;

    @Column(name = "sensor_sample_data",columnDefinition = "mediumtext")
    private String sensorSampleData;


    @Column(name = "sensor_meta",columnDefinition = "mediumtext")
    private String sensorMeta;


    @Column(name = "description")
    private String description;


    @Column(name = "latitude")
    private float latitude = 0;

    @Column(name = "longitude")
    private float longitude = 0;

    @Column(name ="quantity_kind")
    private String quantityKind;

    @Column(name = "unit_of_measurement")
    private String unitOfMeasurement;

    private  int ruleType;

    private String nonExpertOriginalRules;



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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Reasoning name(String name) {
        this.name = name;
        return this;
    }

    public String getNonExpertOriginalRules() {
        return nonExpertOriginalRules;
    }

    public void setNonExpertOriginalRules(String nonExpertOriginalRules) {
        this.nonExpertOriginalRules = nonExpertOriginalRules;
    }

    public String getHashedSensor() {
        return hashedSensor;
    }

    public void setHashedSensor(String hashedSensor) {
        this.hashedSensor = hashedSensor;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public String getSensorEndp() {
        return sensorEndp;
    }

    public void setSensorEndp(String sensorEndp) {
        this.sensorEndp = sensorEndp;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public Reasoning userId(String userId) {
        this.userId = userId;
        return this;
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

    public Reasoning content(String content) {
        this.content = content;
        return this;
    }




    public void setContent(String content) {
        this.content = content;
    }

    public String getSensor() {
        return sensor;
    }

    public Reasoning sensor(String sensor) {
        this.sensor = sensor;
        return this;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getDescription() {
        return description;
    }

    public Reasoning description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reasoning reasoning = (Reasoning) o;
        if (reasoning.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reasoning.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reasoning{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", userId='" + userId + '\'' +
            ", created=" + created +
            ", updated=" + updated +
            ", content='" + content + '\'' +
            ", sensor='" + sensor + '\'' +
            ", sensorEndp='" + sensorEndp + '\'' +
            ", sensorSampleData='" + sensorSampleData + '\'' +
            ", sensorMeta='" + sensorMeta + '\'' +
            ", description='" + description + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", quantityKind='" + quantityKind + '\'' +
            ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
            '}';
    }
}
