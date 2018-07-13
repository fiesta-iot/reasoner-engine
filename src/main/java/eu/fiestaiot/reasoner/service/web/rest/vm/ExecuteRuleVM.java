package eu.fiestaiot.reasoner.service.web.rest.vm;

import javax.validation.constraints.NotNull;

public class ExecuteRuleVM {
    @NotNull
    private Long id;

    public ExecuteRuleVM() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ExecuteRuleVM{" +
            "id=" + id +
            '}';
    }
}
