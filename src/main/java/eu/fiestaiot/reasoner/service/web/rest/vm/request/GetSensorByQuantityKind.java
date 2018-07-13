package eu.fiestaiot.reasoner.service.web.rest.vm.request;

import javax.validation.constraints.NotNull;

/**
 * Created by hungnguyendang on 25/07/2017.
 */
public class GetSensorByQuantityKind {
    @NotNull
    private String quantityKind;

    public GetSensorByQuantityKind() {
    }

    public GetSensorByQuantityKind(String quantityKind) {
        this.quantityKind = quantityKind;
    }

    public String getQuantityKind() {
        return quantityKind;
    }

    public void setQuantityKind(String quantityKind) {
        this.quantityKind = quantityKind;
    }

    @Override
    public String toString() {
        return "GetSensorByQuantityKind{" +
            "quantityKind='" + quantityKind + '\'' +
            '}';
    }
}
