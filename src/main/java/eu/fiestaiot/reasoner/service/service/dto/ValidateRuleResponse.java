package eu.fiestaiot.reasoner.service.service.dto;

/**
 * Created by hungnguyendang on 20/07/2017.
 */
public class ValidateRuleResponse {
    private Boolean result;
    private String message;

    public ValidateRuleResponse() {
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ValidateRuleResponse{" +
            "result=" + result +
            ", message='" + message + '\'' +
            '}';
    }
}
