package eu.fiestaiot.reasoner.service.web.rest.vm.request;

import javax.validation.constraints.NotNull;

/**
 * Created by hungnguyendang on 18/07/2017.
 */
public class GetSensorDataRequest {

    @NotNull
    private String id;
    public GetSensorDataRequest() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetSensorDataRequest{" +
            "id='" + id + '\'' +
            '}';
    }
}
