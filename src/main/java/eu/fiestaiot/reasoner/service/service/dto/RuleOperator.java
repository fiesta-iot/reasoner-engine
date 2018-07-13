package eu.fiestaiot.reasoner.service.service.dto;

/**
 * Created by hungnguyendang on 27/07/2017.
 */
public class RuleOperator {

    private float ruleValue;
    private String uom;
    private String infferedData;
    private String qk;
    private String firstOperator;
    private String secondOperator;
    private String thirdOperator;



    public RuleOperator() {
    }

    public RuleOperator(float ruleValue, String uom, String infferedData, String qk, String firstOperator, String secondOperator, String thirdOperator) {
        this.ruleValue = ruleValue;
        this.uom = uom;
        this.infferedData = infferedData;
        this.qk = qk;
        this.firstOperator = firstOperator;
        this.secondOperator = secondOperator;
        this.thirdOperator = thirdOperator;
    }

    public float getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(float ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getInfferedData() {
        return infferedData;
    }

    public void setInfferedData(String infferedData) {
        this.infferedData = infferedData;
    }

    public String getQk() {
        return qk;
    }

    public void setQk(String qk) {
        this.qk = qk;
    }

    public String getFirstOperator() {
        return firstOperator;
    }

    public void setFirstOperator(String firstOperator) {
        this.firstOperator = firstOperator;
    }

    public String getSecondOperator() {
        return secondOperator;
    }

    public void setSecondOperator(String secondOperator) {
        this.secondOperator = secondOperator;
    }

    public String getThirdOperator() {
        return thirdOperator;
    }

    public void setThirdOperator(String thirdOperator) {
        this.thirdOperator = thirdOperator;
    }

    @Override
    public String toString() {
        return "RuleOperator{" +
            "ruleValue='" + ruleValue + '\'' +
            ", uom='" + uom + '\'' +
            ", infferedData='" + infferedData + '\'' +
            ", qk='" + qk + '\'' +
            ", firstOperator='" + firstOperator + '\'' +
            ", secondOperator='" + secondOperator + '\'' +
            ", thirdOperator='" + thirdOperator + '\'' +
            '}';
    }
}
