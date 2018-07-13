package eu.fiestaiot.reasoner.service.service.util;

import eu.fiestaiot.reasoner.service.domain.RegisterRule;
import eu.fiestaiot.reasoner.service.service.dto.SensorData;
import eu.fiestaiot.reasoner.service.service.impl.RegisterRuleServiceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by hungnguyendang on 21/08/2017.
 */

@Service
public class ParserUtil {

    private final Logger log = LoggerFactory.getLogger(ParserUtil.class);

    public List<SensorData> parseListSensors(String jsonData) {

        log.info("parseListSensors Start parseListSensors: {}", jsonData);

        List<SensorData> result = null;
        try {

            if (jsonData == null || jsonData.isEmpty()) {
                return result;
            }
            JSONObject jsonObject1 = (JSONObject) new JSONParser().parse(jsonData);


            JSONArray list = (JSONArray) jsonObject1.get("items");

            result = new ArrayList<>();
            int lengh = list.size();

            for (int i = 0; i < lengh; i++) {

                JSONObject data = (JSONObject) list.get(i);
                if (data != null) {
                    log.info("parseListSensors data:{}", data);

//                    {
//                        "dateTime":"2018-07-08T14:45:00Z^^http:\/\/www.w3.org\/2001\/XMLSchema#dateTime", "unit":
//                        "http:\/\/purl.org\/iot\/vocab\/m3-lite#Watt", "observation":
//                        "https:\/\/platform.fiesta-iot.eu\/iot-registry\/api\/observations\/L3G7chjmN6UxrWXlF2dwX_2Z1aJU8ubOf5YLiHEw-JR7LsgXT_zh-sg7Ts8ITfZp0EwJcrzVQeBmC8Y97ksvAQOkywH6T1qQ7fHxJexqxr4=", "sensingDevice":
//                        "https:\/\/platform.fiesta-iot.eu\/iot-registry\/api\/resources\/VsnDY_ipIeAhy2eCc5jxNRqGyBVsIwso2bO-8KCr7GKnfKLgda8TdXItkjaADUHLb6VnxSxvR7MddDzbM9fR-Crr9BuuRehd9QCZYPKVzsuaAvFxz6BhRc_PTWFEzu2I",
// "dataValue":   "8.59202E-1^^http:\/\/www.w3.org\/2001\/XMLSchema#double",


// "sensorOutput":
//                        "https:\/\/platform.fiesta-iot.eu\/iot-registry\/api\/observations\/ja28LOqbfgy8kCsMNV3vo2e4nf7cLPxsD7Zbizxd0Xp0dHXbEwltSOMq9IcDZOciqqirC4StE8E4cNuuqRVsUv529H0awUhhGLx3EwLG_yE=", "obsValue":
//                        "https:\/\/platform.fiesta-iot.eu\/iot-registry\/api\/observations\/kbtuVXywX_OHHeVqva_yWKgYKXbS6VntinBFsNFnkZCdKLD5tmke17oLTAJB-L0I6YBvoUV8CytCFVXKxlPXEoAwu97wUgU9SO7EWPj1WqAwEOrN6N1nHJCLsUC1gQWX", "quantityKind":
//                        "http:\/\/purl.org\/iot\/vocab\/m3-lite#Power", "instant":
//                        "https:\/\/platform.fiesta-iot.eu\/iot-registry\/api\/observations\/Qj5UyhpM6MtzmiHU6yaZZD3tTnjb5GdAP7kjV9b5fUYC4O3YqmQjxUuKtLbqr5YGQ-ZlBpOE6XGL2ymUMzY_r3BqjBNxcTCIQwNNnRGQTKb1XgJs3bgM-6TTyoBX9q2J"
//                    }

                    log.info("data.get(\"sensorOutput\").toString():{}", data.get("sensorOutput").toString());
                    log.info("data.get(\"obsValue\").toString(): {}", data.get("obsValue").toString());
                    log.info("data.get(\"observation\").toString(): {}", data.get("observation").toString());
                    log.info("data.get(\"instant\").toString(): {}", data.get("instant").toString());
                    log.info("data.get(\"dateTime\").toString(): {}", data.get("dateTime").toString());
                    log.info("data.get(\"sensingDevice\").toString(): {}", data.get("sensingDevice").toString());

                    log.info("data.get(\"dataValue\").toString(): {}", data.get("dataValue").toString());


                    SensorData sensorData = new SensorData();
                    sensorData.setSensorOutput(data.get("sensorOutput").toString());
                    sensorData.setObsValue(data.get("obsValue").toString());
                    sensorData.setObservation(data.get("observation").toString());
                    sensorData.setInstant(data.get("instant").toString());
                    sensorData.setDateTime(data.get("dateTime").toString());
                    sensorData.setSensingDevice(data.get("sensingDevice").toString());

                    Double dataValue = parseDataValue(data);
                    if(dataValue != null) {
                        sensorData.setDataValue(dataValue);
                        result.add(sensorData);
                    }
                }
            }

        } catch (ParseException e) {
            log.info("ParseException ex: {}", e.toString());
            e.printStackTrace();
            //result = null;
        }
        log.info("-------  final result---------------: {}", result);
        return result;
    }
    private  Double parseDataValue(JSONObject data) {

        try {
            log.info("parse data value");
            String dataValueString = data.get("dataValue").toString();
            log.info("dataValueString: {}", dataValueString);
            String doubleValue = dataValueString.substring(0, dataValueString.indexOf("^^"));
            log.info("doubleValue: {}", doubleValue);

            Double value = Double.parseDouble(doubleValue);
            log.info("value: {}", value);

            return value;
        }catch (Exception ex) {
            return null;
        }

    }

//    public static void main(String args[] ) {
//        String dataValueString = "8.59202E-1^^http:\\/\\/www.w3.org\\/2001\\/XMLSchema#double";
//        String doubleValue = dataValueString.substring(0, dataValueString.indexOf("^^"));
//
//        Double value = Double.parseDouble(doubleValue);
//        System.out.println("value:" + value);
//
//    }


    public org.json.JSONObject putDataObjectValue(org.json.JSONObject object, SensorData sensorData) {
        Set<String> keys = object.keySet();
        for (String key : keys) {
            if (key.contains("#hasDataValue")) {
                object.put(key, sensorData.getDataValue());
                object.put("@id", sensorData.getObservation());
                log.info("get data object: {}", object);
                return object;
            }
        }
        return object;
    }

    public org.json.JSONObject getCloneDataObject(org.json.JSONArray list) {
        for (int i = 0; i < list.length(); i++) {
            org.json.JSONObject object = list.getJSONObject(i);
            if (object.getString("@type") != null && object.getString("@type").equalsIgnoreCase("http://purl.oclc.org/NET/ssnx/ssn#ObservationValue")) {
                return object;
            }
        }
        return null;
    }

    public org.json.JSONObject getCloneObservationObject(org.json.JSONArray list) {
        for (int i = 0; i < list.length(); i++) {
            org.json.JSONObject object = list.getJSONObject(i);
            if (object.getString("@type") != null && object.getString("@type").equalsIgnoreCase("http://purl.oclc.org/NET/ssnx/ssn#Observation")) {

                return object;
            }
        }
        return null;
    }

    public org.json.JSONObject putObservationObject(org.json.JSONObject cloneObservationObject, SensorData sensorData) {
        //cloneObservationObject.put("observationResult",  sensorData.getObservation());
        cloneObservationObject.put("@id", sensorData.getObsValue());
        return cloneObservationObject;
    }

    public String parserSensorData(String endpointContent, List<SensorData> sensorDatas, RegisterRule registerRule) {

        try {

            org.json.JSONObject sensorEndpointData = new org.json.JSONObject(endpointContent);
            org.json.JSONArray list = sensorEndpointData.getJSONArray("@graph");
            List<org.json.JSONObject> results = new ArrayList<>();

            org.json.JSONObject observationObject = getCloneObservationObject(list);
            org.json.JSONObject dataObject = getCloneDataObject(list);

            for (SensorData sensorData : sensorDatas) {
                org.json.JSONObject cloneObservationObject = new org.json.JSONObject(observationObject.toString());
                org.json.JSONObject clonedataObject = new org.json.JSONObject(dataObject.toString());
                clonedataObject = putDataObjectValue(clonedataObject, sensorData);
                cloneObservationObject = putObservationObject(cloneObservationObject, sensorData);

                list.put(clonedataObject);
                list.put(cloneObservationObject);

                //results.add(clonedataObject);
                //results.add(cloneObservationObject);
            }

            sensorEndpointData.put("@graph", list);
            return sensorEndpointData.toString();
        } catch (Exception ex) {
            log.info("Error parsing data: {}", ex.toString());
            return null;
        }

       /* 1. Copy 2 objec
            2. replace

        {
            "@id" :"sics:observation#Vo0ELXE9zy",
            "@type" :"ssn:Observation",
            "observationResult" :"sics:sensorOutput#Vo0ELXE9zy",
            "observationSamplingTime" :"sics:timeInterval#UTC_BwvRqWKMbm",
            "observedBy" :"sics:resource/sc-sics-de-116-temp",
            "observedProperty" :"sics:observationProperty#Temperature",
            "location" :"sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-111"


        }

        {
            "@id" : "sics:observationValue#Vo0ELXE9zy",
            "@type" : "ssn:ObservationValue",
            "hasUnit" : "sics:unit#DegreeCelsius",
            "dul:hasDataValue" : 29.7593727112
        }



        {
            "hasUnit":"https://platform-dev.fiesta-iot.eu/iot-registry/api/observations/m3dXvfvR8Ui5rwugaOajNqQWh9e3bN5Bt7n6DkIHmxSkYG9dsrLuA6XIsWRhedIfYXEF9CfuQXKQc5qhWOqC17Mj_nZW8jgqAtpOKrjekDg=",
            "@type":"http://purl.oclc.org/NET/ssnx/ssn#ObservationValue",
            "http://www.loa.istc.cnr.it/ontologies/DUL.owl#hasDataValue":38.89081,
            "@id":"https://platform-dev.fiesta-iot.eu/iot-registry/api/observations/8j6FM89Of0bhd0rgsDR-kibUyXuPM5QFHn9fSFZIoBRjan3fRaO6nFx76mjRh-rP6AOH5iGTSAtgxFm9zn0hA5HUa_9U8VqmDevIe95q2N8gUpYEwnhc5XaSIf5jZqe12"
        },


        {
            "observedBy":"https://platform-dev.fiesta-iot.eu/iot-registry/api/resources/Ur7Q-GLgxiLsfK4ZhXffEryue052DxDQzb8jxqKMPyLJZUiTr-ZpAj1ZK_hi302o5gp8V6Fe1a2jEzg_STnJkUCQHp8f7qAg1DiohqUnfcll3289LvfcuRmXiDPfZROl",
            "observationResult":"https://platform-dev.fiesta-iot.eu/iot-registry/api/observations/0tM9WwbPkwYN-TWk0zYUDAMyVwKTslWlbZPPUpP5pJG3wcj2PjzMYa_dCKsoAUp42yPciPzMVxOmGZZubbv-0oOSNrIk4mXs8neBqmx4KgY=",
            "@type":"http://purl.oclc.org/NET/ssnx/ssn#Observation",
            "observationSamplingTime":"https://platform-dev.fiesta-iot.eu/iot-registry/api/observations/qHUX5KYZIdzK7fppWXlZYV2n0J7QGqJKS-ah6znnidD_Lj26roj91Ok0Rt7RQZdPRjzkLg9HXrjO4TvDiDfgX4vDWf5CRBMcL8Q1wcrjyCcAwyogCzTVfNrC0Dy4gE4k",
            "observedProperty":"https://platform-dev.fiesta-iot.eu/iot-registry/api/observations/gZm9c50r29ZUeptzuchE8i5TsLUzVdVBQAzVvJMAp4KllDnFoevbUpQCy_yXiCH8Sd9AXqL3q9RL9SJMkQp3xfTfuhCDIl5lkmNJavpxCY2297X68gUCKlone4pvydi3",
            "location":"https://platform-dev.fiesta-iot.eu/iot-registry/api/observations/N2txu0i076-9MP_jrBBWChrqbMmYtIfvjwo_y0ydNpAVbbmZjyGFIfqYfn5Sarq07oujYvkJD81GI4s1IjhkmDPpo-CjsJySVhS548-7XfqxqJUqKOpXvIsFXdaf5K_wV1hNwidU3-N2yGPYFMB3tA==",
            "@id":"https://platform-dev.fiesta-iot.eu/iot-registry/api/observations/pWIltCngrnkRu9ssWx0XZnPH0Vy91-o7X3rcyrZY-pDGbSjtI-JiRngCJ_F9lbht71j5k8WQy8ymZ5H1iwOdzb9kMQliVVxOWGW1UfOlbto=2"
        }*/


    }

    public String parse(String endpointContent, List<SensorData> sensorDatas, RegisterRule registerRule) throws IOException, ParseException {


        JSONObject jsonObject1 = (JSONObject) new JSONParser().parse(endpointContent);
        JSONArray list = (JSONArray) jsonObject1.get("@graph");
        JSONArray results = new JSONArray();

        JSONObject geoObject = (JSONObject) list.get(0);
        if (geoObject != null) {
            results.add(geoObject);
        }

        for (SensorData sensorData : sensorDatas) {

            JSONObject observation = new JSONObject();

            observation.put("@id", "fiesta-iot:observation#" + sensorData.getObservation());
            observation.put("@type", "ssn:Observation");
            observation.put("observationResult", "fiesta-iot:sensorOutput#" + sensorData.getObservation());
            observation.put("observationSamplingTime", "fiesta-iot:timeInterval#" + sensorData.getInstant());
            //observation.put("observedBy",sensorData.getSensingDevice());
            observation.put("observedProperty", registerRule.getQuantityKind());


            JSONObject observationValue = new JSONObject();
            observationValue.put("@id", "fiesta-iot:observationValue#" + sensorData.getObservation());
            observationValue.put("@type", "ssn:ObservationValue");
            observationValue.put("hasUnit", registerRule.getUnitOfMeasurement());
            observationValue.put("dul:hasDataValue", sensorData.getDataValue());


            JSONObject sensorOutput = new JSONObject();
            sensorOutput.put("@id", "fiesta-iot:sensorOutput#" + sensorData.getObservation());
            sensorOutput.put("@type", "ssn:SensorOutput");
            sensorOutput.put("hasValue", "fiesta-iot:observationValue#" + sensorData.getObservation());


            JSONObject quantityKind = new JSONObject();
            quantityKind.put("@id", registerRule.getQuantityKind());
            quantityKind.put("@type", registerRule.getQuantityKind());

            JSONObject unitOf = new JSONObject();
            unitOf.put("@id", registerRule.getUnitOfMeasurement());
            unitOf.put("@type", registerRule.getUnitOfMeasurement());

            JSONObject instant = new JSONObject();
            instant.put("@id", "fiesta-iot:timeInterval#" + sensorData.getInstant());
            instant.put("@type", "time:Instant");
            instant.put("inXSDDateTime", sensorData.getDateTime());


            results.add(observation);

            results.add(observationValue);
            results.add(sensorOutput);
            results.add(instant);
            results.add(quantityKind);
            results.add(unitOf);

        }

        jsonObject1.put("@graph", results);
        return jsonObject1.toJSONString();

    }


}
