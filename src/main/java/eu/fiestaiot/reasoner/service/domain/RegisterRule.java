package eu.fiestaiot.reasoner.service.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A RegisterRule.
 */
@Entity
@Table(name = "register_rule")
public class RegisterRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "rule_content", nullable = false,columnDefinition = "mediumtext")
    private String ruleContent;

    @NotNull
    @Column(name = "data", nullable = false,columnDefinition = "mediumtext")
    private String data;

    @NotNull
    @Column(name = "sensor", nullable = false,columnDefinition = "varchar(300)")
    private String sensor;

    private String hashedSensor;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @Column(name ="quantity_kind")
    private String quantityKind;

    @Column(name = "unit_of_measurement")
    private String unitOfMeasurement;

    @Column(name = "sensor_endp",columnDefinition = "varchar(300)")
    private String sensorEndp;

    @Column(name = "sensor_meta",columnDefinition = "mediumtext")
    private String sensorMeta;

    @Column(name = "inferred_data",columnDefinition = "mediumtext")
    private String inferredData;

    @Column(name = "full_data",columnDefinition = "mediumtext")
    private String fullData;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "created")
    private Date created;
    @Column(name = "updated")
    private Date updated;

    @ManyToOne
    @NotNull
    private Reasoning reasoning;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RegisterRule name(String name) {
        this.name = name;
        return this;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public RegisterRule description(String description) {
        this.description = description;
        return this;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public RegisterRule ruleContent(String ruleContent) {
        this.ruleContent = ruleContent;
        return this;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public String getData() {
        return data;
    }

    public RegisterRule data(String data) {
        this.data = data;
        return this;
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

    public void setData(String data) {
        this.data = data;
    }

    public String getSensor() {
        return sensor;
    }

    public RegisterRule sensor(String sensor) {
        this.sensor = sensor;
        return this;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getSensorMeta() {
        return sensorMeta;
    }

    public RegisterRule sensorMeta(String sensorMeta) {
        this.sensorMeta = sensorMeta;
        return this;
    }

    public void setSensorMeta(String sensorMeta) {
        this.sensorMeta = sensorMeta;
    }

    public String getInferredData() {
        return inferredData;
    }

    public RegisterRule inferredData(String inferredData) {
        this.inferredData = inferredData;
        return this;
    }

    public void setInferredData(String inferredData) {
        this.inferredData = inferredData;
    }

    public String getFullData() {
        return fullData;
    }

    public RegisterRule fullData(String fullData) {
        this.fullData = fullData;
        return this;
    }

    public void setFullData(String fullData) {
        this.fullData = fullData;
    }

    public String getUserId() {
        return userId;
    }

    public RegisterRule userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Reasoning getReasoning() {
        return reasoning;
    }

    public RegisterRule reasoning(Reasoning reasoning) {
        this.reasoning = reasoning;
        return this;
    }

    public void setReasoning(Reasoning reasoning) {
        this.reasoning = reasoning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegisterRule registerRule = (RegisterRule) o;
        if (registerRule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, registerRule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RegisterRule{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", ruleContent='" + ruleContent + '\'' +
            ", data='" + data + '\'' +
            ", sensor='" + sensor + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", quantityKind='" + quantityKind + '\'' +
            ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
            ", sensorMeta='" + sensorMeta + '\'' +
            ", inferredData='" + inferredData + '\'' +
            ", fullData='" + fullData + '\'' +
            ", userId='" + userId + '\'' +
            ", reasoning=" + reasoning +
            '}';
    }
}
