package eu.fiestaiot.reasoner.service.service.util;

import eu.fiestaiot.reasoner.service.service.dto.RuleOperator;
import eu.fiestaiot.reasoner.service.web.rest.ReasoningResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnguyendang on 27/07/2017.
 */
public class RuleUtils {

    //private final Logger log = LoggerFactory.getLogger(RuleUtils.class);
    /*
    public static void main(String args[]) {
        RuleOperator operator = new RuleOperator();
        operator.setQk("Power");
        operator.setUom("Watt");
        operator.setFirstOperator("IF");
        operator.setRuleValue(1.0f);
        operator.setSecondOperator(">");
        operator.setInfferedData("danger");


        RuleOperator operator2 = new RuleOperator();
        operator2.setQk("Power");
        operator2.setUom("Watt");
        operator2.setFirstOperator("IF");
        operator2.setRuleValue(1.0f);
        operator2.setSecondOperator("<");
        operator2.setInfferedData("low");


        List<RuleOperator> list = new ArrayList<>();
        list.add(operator);
        list.add(operator2);

        String ruleContent = translateRule(list);

        System.out.println(ruleContent);

    }*/
    public  static String translateRules(List<RuleOperator> rules) {
        String ruleTemplate = "@prefix iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#> .\n" +
            "@prefix m3-lite: <http://purl.org/iot/vocab/m3-lite#> .\n" +
            "@prefix ssn: <http://purl.oclc.org/NET/ssnx/ssn#> .\n" +
            "@prefix geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> .\n" +
            "@prefix xsd:    <http://www.w3.org/2001/XMLSchema#> .\n" +
            "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
            "@prefix dul: <http://www.loa.istc.cnr.it/ontologies/DUL.owl#> .\n" +
            "@prefix time: <http://www.w3.org/2006/time#> .\n" +
            "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" +
            "\n";


        for(RuleOperator rule: rules) {
            System.out.println("--- rule operator --- : {}" + rule.getSecondOperator());

            if(rule.getSecondOperator().equalsIgnoreCase(">")) {
                rule.setSecondOperator("greaterThan");
            }
            if(rule.getSecondOperator().equalsIgnoreCase("<") ){
                rule.setSecondOperator("lessThan");
            }
            if(rule.getSecondOperator().equalsIgnoreCase("=") ){
                rule.setSecondOperator("equal");
            }
            if(rule.getSecondOperator().equalsIgnoreCase("!=")) {
                rule.setSecondOperator("notEqual");
            }

            if(rule.getUom().contains("(")) {
                rule.setUom(rule.getUom().substring(0, rule.getUom().indexOf("(")).trim());
            }

            String operatorTemplate = MessageFormat.format("(?observation rdf:type ssn:Observation),\n" +
                "(?observation ssn:observedProperty ?observedProperty),\n" +
                "(?observedProperty rdf:type m3-lite:{0}),\n" +
                "(?observation ssn:observationResult ?sensorOutput),\n" +
                "(?sensorOutput ssn:hasValue ?obsValue),\n" +
                "(?obsValue dul:hasDataValue ?dataValue),\n" +
                "(?obsValue iot-lite:hasUnit ?unit),\n" +
                "(?unit rdf:type m3-lite:{1}),\n" +
                "{2}(?dataValue, \"{3}\"^^xsd:double) -> (?observation <https://fiesta-iot.eu/reasoning/announce#> \"{4}\"^^xsd:string).", rule.getQk(), rule.getUom(),rule.getSecondOperator(), rule.getRuleValue(),rule.getInfferedData());

            ruleTemplate = ruleTemplate + operatorTemplate;

        }


        return ruleTemplate;
    }

    public  static String translateRule(List<RuleOperator> rules) {
        String ruleTemplate = "@prefix iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#> .\n" +
            "@prefix m3-lite: <http://purl.org/iot/vocab/m3-lite#> .\n" +
            "@prefix ssn: <http://purl.oclc.org/NET/ssnx/ssn#> .\n" +
            "@prefix geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> .\n" +
            "@prefix xsd:    <http://www.w3.org/2001/XMLSchema#> .\n" +
            "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
            "@prefix dul: <http://www.loa.istc.cnr.it/ontologies/DUL.owl#> .\n" +
            "@prefix time: <http://www.w3.org/2006/time#> .\n" +
            "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" +
            "@prefix reasoning: <https://fiesta-iot.eu/reasoning#> .";


        for(RuleOperator rule: rules) {
            System.out.println("--- rule operator --- : {}" + rule.getSecondOperator());

            if(rule.getSecondOperator().equalsIgnoreCase(">")) {
                rule.setSecondOperator("greaterThan");
            }
            if(rule.getSecondOperator().equalsIgnoreCase("<") ){
                rule.setSecondOperator("lessThan");
            }
            if(rule.getSecondOperator().equalsIgnoreCase("=") ){
                rule.setSecondOperator("equal");
            }
            if(rule.getSecondOperator().equalsIgnoreCase("!=")) {
                rule.setSecondOperator("notEqual");
            }

            if(rule.getUom().contains("(")) {
                rule.setUom(rule.getUom().substring(0, rule.getUom().indexOf("(")).trim());
            }


            String operatorTemplate = MessageFormat.format(
                "(?observation rdf:type ssn:Observation),\n" +
                "(?observation ssn:observedProperty ?observedProperty),\n" +
                "(?observedProperty rdf:type m3-lite:{0}),\n" +
                "(?observation ssn:observationResult ?sensorOutput),\n" +
                "(?sensorOutput ssn:hasValue ?obsValue),\n" +
                "(?obsValue dul:hasDataValue ?dataValue),\n" +
                "(?obsValue iot-lite:hasUnit ?unit),\n" +
                "(?unit rdf:type m3-lite:{1}),\n" +
                "{2}(?dataValue, \"{3}\"^^xsd:double) -> (?observation reasoning:announce \"{4}\"^^xsd:string).", rule.getQk(), rule.getUom(),rule.getSecondOperator(), rule.getRuleValue(),rule.getInfferedData());

            ruleTemplate = ruleTemplate + operatorTemplate;

        }


        return ruleTemplate;
    }

    /*
    @prefix iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#> .
@prefix m3-lite: <http://purl.org/iot/vocab/m3-lite#> .
@prefix ssn: <http://purl.oclc.org/NET/ssnx/ssn#> .
@prefix geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> .
@prefix xsd:    <http://www.w3.org/2001/XMLSchema#> .
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dul: <http://www.loa.istc.cnr.it/ontologies/DUL.owl#> .
@prefix time: <http://www.w3.org/2006/time#> .
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix reasoning: <https://fiesta-iot.eu/reasoning#> .

(?observation rdf:type ssn:Observation),
(?observation ssn:observedProperty ?observedProperty),
(?observedProperty rdf:type m3-lite:Power),
(?observation ssn:observationResult ?sensorOutput),
(?sensorOutput ssn:hasValue ?obsValue),
(?obsValue dul:hasDataValue ?dataValue),
(?obsValue iot-lite:hasUnit ?unit),
(?unit rdf:type m3-lite:Watt),
greaterThan(?dataValue, "25"^^xsd:double) -> (?observation reasoning:announce "dangerous_notify"^^xsd:string).

(?observation rdf:type ssn:Observation),
(?observation ssn:observedProperty ?observedProperty),
(?observedProperty rdf:type m3-lite:Power),
(?observation ssn:observationResult ?sensorOutput),
(?sensorOutput ssn:hasValue ?obsValue),
(?obsValue dul:hasDataValue ?dataValue),
(?obsValue iot-lite:hasUnit ?unit),
(?unit rdf:type m3-lite:Watt),
lessThan(?dataValue, "23"^^xsd:double) -> (?observation reasoning:announce "lowpower_notify"^^xsd:string).

     */
}
