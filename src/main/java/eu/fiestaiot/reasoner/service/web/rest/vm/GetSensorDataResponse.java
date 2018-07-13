package eu.fiestaiot.reasoner.service.web.rest.vm;

/**
 * Created by hungnguyendang on 18/07/2017.
 */
public class GetSensorDataResponse {

    public String data;

    public GetSensorDataResponse(String data) {
        this.data = data;
    }

    public GetSensorDataResponse() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetSensorDataResponse{" +
            "data='" + data + '\'' +
            '}';
    }
}
