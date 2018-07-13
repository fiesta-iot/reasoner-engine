package eu.fiestaiot.reasoner.service.web.rest.vm;


import eu.fiestaiot.reasoner.service.service.dto.Sensor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Reasoning.
 */

public class ReasoningVM implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;
    @NotNull

    private String content;

    @NotNull
    private Sensor sensor;

    private String description;


    public ReasoningVM() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
