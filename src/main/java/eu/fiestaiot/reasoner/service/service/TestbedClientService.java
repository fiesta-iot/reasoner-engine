package eu.fiestaiot.reasoner.service.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsonldjava.utils.Obj;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import eu.fiestaiot.reasoner.service.config.FiestaProperties;
import eu.fiestaiot.reasoner.service.service.dto.Sensor;
import eu.fiestaiot.reasoner.service.service.dto.SensorData;
import org.apache.jena.atlas.json.JSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.crypto.Cipher;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by hungnguyendang on 21/06/2017.
 */
@Service
public class TestbedClientService {

    private final Logger log = LoggerFactory.getLogger(TestbedClientService.class);
    Cipher cipher;

    private static final int MAX_TRIM_HASH = 12;

    @Inject
    private FiestaProperties fiestaTestbedProperties;

    public List<String> getTestbeds(String userID, String SSOtoken) {

        List<String> result = null;
        try {

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();
            log.info("start getting testbeds by userID from ui.testbed-registry service with user id : {} and SSOtoken: {}", userID, SSOtoken);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(platformBaseURL + "ui.testbed-registry/api/getAllTestbeds")
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .asJson();

            log.info("response status code:{} ", jsonResponse.getStatus());

            if (jsonResponse.getStatus() == 200) {
                log.info("json response:{}", jsonResponse.getBody().getArray());
                result = new ArrayList<>();
                if (jsonResponse.getBody() != null && jsonResponse.getBody().getArray() != null) {
                    int size = jsonResponse.getBody().getArray().length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = jsonResponse.getBody().getArray().getJSONObject(i);
                        result.add(jsonObject.get("iri").toString());
                    }
                }
                return result;
            } else {
                log.info("error response status : {}, body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());

            }

        } catch (UnirestException e) {
            log.error("exception message:" + e.toString());
            e.printStackTrace();

        }
        log.info("end call service");
        return result;
    }

    public List<String> getAllTestbeds(String SSOtoken) {

        List<String> result = null;
        try {

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            log.info("start getting testbeds by userID from ui.testbed-registry service  SSOtoken: {}", SSOtoken);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(platformBaseURL + "ui.testbed-registry/api/getAllTestbeds")
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .asJson();

            log.info("response status code:{} ", jsonResponse.getStatus());

            if (jsonResponse.getStatus() == 200) {
                log.info("json response:{}", jsonResponse.getBody().getArray());
                result = new ArrayList<>();
                if (jsonResponse.getBody() != null && jsonResponse.getBody().getArray() != null) {
                    int size = jsonResponse.getBody().getArray().length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = jsonResponse.getBody().getArray().getJSONObject(i);
                        result.add(jsonObject.get("iri").toString());
                    }
                }
                return result;
            } else {
                log.info("error response status : {}, body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());

            }

        } catch (UnirestException e) {
            log.error("exception message:" + e.toString());
            e.printStackTrace();

        }
        log.info("end call service");
        return result;
    }

    public String getTestbedResource(String testbed, String SSOtoken) {
        String result = "";
        try {

            log.info("start getting testbed resource by IRI: {} and token: {}", testbed, SSOtoken);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/utils/identifier/to_fiesta_iot?type=testbed")
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .body("[\"" + testbed + "\"]")
                .asJson();

            log.info("reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("response body:" + jsonResponse.getBody().getArray());
                result = jsonResponse.getBody().getArray().get(0).toString();
            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = "";
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        }
        log.info("end call service");
        return result;
    }

    public List<String> getTestbedSensors(String testbed, String SSOtoken) {
        List<String> result = null;
        try {

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();


            String resourceUri = platformBaseURL + "iot-registry/api/testbeds/" + testbed + "/global";
            log.info("start getting resource sensors by testbed resource id: {}, and token: {}", testbed, SSOtoken);

            log.info("service full uri: {}", resourceUri);

            HttpResponse<JsonNode> jsonResponse = Unirest.get(resourceUri)
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .asJson();
            log.info("response status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {
                log.info("success call service with response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                ObjectMapper objectMapper = new ObjectMapper();
                result = objectMapper.readValue(jsonResponse.getBody().getObject().get("resources").toString(), new TypeReference<List<String>>() {
                });
                return result;
            } else {
                log.info("error call service with response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());

            }

        } catch (UnirestException e) {
            log.error("error: {}", e.toString());
            e.printStackTrace();
            result = null;
        } catch (JsonParseException e) {
            log.error("error: {}", e);
            e.printStackTrace();
            result = null;
        } catch (JsonMappingException e) {
            log.error("error : {}", e);
            e.printStackTrace();
            result = null;
        } catch (IOException e) {
            log.error("error: {}", e);
            e.printStackTrace();
            result = null;
        }
        log.info("end call service");
        return result;

    }


    public List<Sensor> getSensorsFullInfo(String SSOToken) {

        List<String> listTestbeds = getAllTestbeds(SSOToken);
        List<Sensor> results;
        if (listTestbeds != null) {
            results = new ArrayList<>();
            for (String testbed : listTestbeds) {
                List<String> sensors = getTestbedSensors(testbed, SSOToken);
                if (sensors != null) {
                    for (String sensor : sensors) {
                        // Sensor sensorObject = new Sensor();
                        //sensorObject.setSensor(sensor);
                        //results.add(sensorObject);

                    }
                    ;
                }
            }

        }
        return null;
    }

    public String getSensorOriginalIDByHashedId(String hashID, String SSOToken) {
        String result = null;
        try {

            //https://platform-dev.fiesta-iot.eu/iot-registry/api/resources/DaCKKYlDSdTp1u0a6aQfAzmQh-Sd4XXYewDTaSCR1uT6NXN9S1iP73ytL_NC_XyqqZ9bac7D0zvxnPoFb8zDKWqsrx0fYQ14UxuIMDnLrtfMvJuGY7fbdnsLsXYTYrls/original_id
            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();


            String resourceUri = hashID + "/original_id";
            log.info("start getting resource sensors by testbed resource id: {}, and token: {}", hashID, SSOToken);

            log.info("service full uri: {}", resourceUri);

            HttpResponse<String> stringResponse = Unirest.get(resourceUri)
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOToken)
                .asString();
            log.info("response status code: {}", stringResponse.getStatus());
            if (stringResponse.getStatus() == 200) {
                log.info("success call service with response status code: {} and body: {}", stringResponse.getStatus(), stringResponse.getBody().toString());
                result = stringResponse.getBody().toString();

            } else {
                log.info("error call service with response status code: {} and body: {}", stringResponse.getStatus(), stringResponse.getBody().toString());

            }
        } catch (Exception ex) {
            log.info("Exeption: {}", ex);
            result = null;

        }
        return result;
    }

    public List<Sensor> getSensors(String SSOToken) {
        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "\n" +
            "SELECT ?sensor ?type ?qk ?unit ?endp ?lat ?long   \n" +
            "WHERE {\n" +
            "\t#Device as the basis\n" +
            "\t{\n" +
            "    \t?dev rdf:type/rdfs:subClassOf ssn:Device .\n" +
            "    \t?dev rdf:type ?type .\n" +
            "    \t?dev ssn:onPlatform ?platform .\n" +
            "    \t?platform geo:location ?point .\n" +
            "    \t?point geo:lat ?lat .\n" +
            "    \t?point geo:long ?long .\n" +
            "    \t?dev ssn:hasSubSystem ?sensor .\n" +
            "    \t?sensor iot-lite:hasQuantityKind ?qkr .\n" +
            "    \t?qkr rdf:type ?qk .\n" +
            "    \t?sensor iot-lite:hasUnit ?unitr .\n" +
            "    \t?unitr rdf:type ?unit .\n" +
            "    \tOPTIONAL {\n" +
            "    \t\t?sensor iot-lite:exposedBy ?serv .\n" +
            "    \t\t?serv iot-lite:endpoint ?endp . \n" +
            "\t\t}\n" +
            "    }\n" +
            "    UNION\n" +
            "    #SensingDevice as the basis\n" +
            "    {\n" +
            "    \t?sensor rdf:type/rdfs:subClassOf ssn:SensingDevice .\n" +
            "    \t?sensor ssn:onPlatform ?platform .\n" +
            "    \t?platform geo:location ?point .\n" +
            "    \t?point geo:lat ?lat .\n" +
            "    \t?point geo:long ?long .\n" +
            "    \t?sensor iot-lite:hasQuantityKind ?qkr .\n" +
            "    \t?qkr rdf:type ?qk .\n" +
            "    \t?sensor iot-lite:hasUnit ?unitr .\n" +
            "    \t?unitr rdf:type ?unit .\n" +
            "    \tOPTIONAL {\n" +
            "    \t\t?sensor iot-lite:exposedBy ?serv .\n" +
            "    \t\t?serv iot-lite:endpoint ?endp . \n" +
            "\t\t}\n" +
            "    }\n" +
            "}\n";

        List<Sensor> result = null;
        try {

            log.info("start getting sensors by token: {}", SSOToken);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();


            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/queries/execute/global")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", SSOToken)
                .body(query)
                .asJson();

            log.info("reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("success call service with response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());

                JSONArray items = (JSONArray) jsonResponse.getBody().getObject().get("items");
                if (items != null && items.length() > 0) {
                    result = new ArrayList<>();

                    int lengh = items.length();
                    for (int i = 0; i < lengh; i++) {
                        JSONObject object = items.getJSONObject(i);

                        log.info("object--------------------: {}", object);

                        String endpoint = null;
                        try {
                            endpoint = object.getString("endp");
                        } catch (Exception exs) {

                        }
                        if (endpoint != null) {

                            String endp = endpoint.substring(0, endpoint.indexOf("^^"));
                            String sensorId = object.get("sensor").toString();
                            String unit = object.get("unit").toString();
                            String qk = object.get("qk").toString();
                            String type = object.get("type").toString();
                            String sensorHahsedId = object.get("sensor").toString();
                            String longStr = object.get("long").toString();
                            String latStr = object.get("lat").toString();
                            String originalID = getSensorOriginalIDByHashedId(sensorId, SSOToken);


                            float lng = Float.parseFloat(longStr.substring(0, longStr.indexOf("^^")));
                            float lat = Float.parseFloat(latStr.substring(0, latStr.indexOf("^^")));

                            Sensor sensor = new Sensor(endp, unit, qk, qk, unit, originalID, sensorHahsedId, originalID, type, lng, lat);
                            result.add(sensor);


                        }


                    }
                }


            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = null;
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = null;
        }
        log.info("end call service");
        return result;
    }

    public List<String> getAllSensors(String SSOToken) {

        /*String query  = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#> \n" +
            "SELECT ?sensor \n" +
            "WHERE { \n" +
            "    # All sensors have to have a QK\n" +
            "    ?sensor iot-lite:hasQuantityKind ?qk .\n" +
            "}";*/

         /*String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
             "PREFIX m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
             "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
             "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
             "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>\n" +
             "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
             "\n" +
             "SELECT  ?sensingDev ?deployment ?relLoc ?endp\n" +
             "WHERE {\n" +
             "    \t?sensingDev a m3-lite:EnergyMeter .\n" +
             "    \t?sensingDev iot-lite:hasQuantityKind ?qk .\n" +
             "    \t?qk a m3-lite:Power .\n" +
             "    \t?sensingDev iot-lite:hasUnit ?unit .\n" +
             "    \t?unit a m3-lite:Watt .\n" +
             "    \t?sensingDev iot-lite:isSubSystemOf ?dev .\n" +
             "    \t?dev a ssn:Device .\t\n" +
             "    \t?dev ssn:onPlatform ?platform .\n" +
             "\t?dev ssn:hasDeployment ?deployment .\n" +
             "    \n" +
             "\t?point iot-lite:relativeLocation ?relLoc .\n" +
             "\t?sensingDev iot-lite:exposedBy ?serv .\n" +
             "\t?serv iot-lite:endpoint ?endp .\n" +
             "  \n" +
             "}ORDER BY ASC(?sensingDev)"; */

        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "\n" +
            "SELECT ?sensor ?type ?qk ?unit ?endp ?lat ?long   \n" +
            "WHERE {\n" +
            "\t#Device as the basis\n" +
            "\t{\n" +
            "    \t?dev rdf:type/rdfs:subClassOf ssn:Device .\n" +
            "    \t?dev rdf:type ?type .\n" +
            "    \t?dev ssn:onPlatform ?platform .\n" +
            "    \t?platform geo:location ?point .\n" +
            "    \t?point geo:lat ?lat .\n" +
            "    \t?point geo:long ?long .\n" +
            "    \t?dev ssn:hasSubSystem ?sensor .\n" +
            "    \t?sensor iot-lite:hasQuantityKind ?qkr .\n" +
            "    \t?qkr rdf:type ?qk .\n" +
            "    \t?sensor iot-lite:hasUnit ?unitr .\n" +
            "    \t?unitr rdf:type ?unit .\n" +
            "    \tOPTIONAL {\n" +
            "    \t\t?sensor iot-lite:exposedBy ?serv .\n" +
            "    \t\t?serv iot-lite:endpoint ?endp . \n" +
            "\t\t}\n" +
            "    }\n" +
            "    UNION\n" +
            "    #SensingDevice as the basis\n" +
            "    {\n" +
            "    \t?sensor rdf:type/rdfs:subClassOf ssn:SensingDevice .\n" +
            "    \t?sensor ssn:onPlatform ?platform .\n" +
            "    \t?platform geo:location ?point .\n" +
            "    \t?point geo:lat ?lat .\n" +
            "    \t?point geo:long ?long .\n" +
            "    \t?sensor iot-lite:hasQuantityKind ?qkr .\n" +
            "    \t?qkr rdf:type ?qk .\n" +
            "    \t?sensor iot-lite:hasUnit ?unitr .\n" +
            "    \t?unitr rdf:type ?unit .\n" +
            "    \tOPTIONAL {\n" +
            "    \t\t?sensor iot-lite:exposedBy ?serv .\n" +
            "    \t\t?serv iot-lite:endpoint ?endp . \n" +
            "\t\t}\n" +
            "    }\n" +
            "}\n";

        List<String> result = null;
        try {

            log.info("start getting sensors by token: {}", SSOToken);

            //Unirest.setTimeouts(60000,60000);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/queries/execute/global")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", SSOToken)
                .body(query)
                .asJson();

            log.info("reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("success call service with response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());

                JSONArray items = (JSONArray) jsonResponse.getBody().getObject().get("items");
                if (items != null && items.length() > 0) {
                    result = new ArrayList<>();
                    int lengh = items.length();
                    for (int i = 0; i < lengh; i++) {
                        JSONObject object = items.getJSONObject(i);
                        result.add(object.get("sensingDev").toString());

                    }
                }


            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = null;
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = null;
        }
        log.info("end call service");
        return result;

    }


    public String getSensorOriginalDataByID(String sensorID, String token) {

        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "\n" +
            "SELECT   ?endp\n" +
            "WHERE {\n" +
            "<" + sensorID + "> iot-lite:exposedBy ?serv ." +
            "\t?serv iot-lite:endpoint ?endp .\n" +
            "  \n" +
            "}";
        log.info("query:{}", query);

        String result = "";
        try {

            log.info("start getting sensor endpoint by sensor ID: {} and token: {}", sensorID, token);
            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();
            log.info("URL: {}", platformBaseURL  + "iot-registry/api/queries/execute/global");

            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/queries/execute/global")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", token)
                .body(query)
                .asJson();

            log.info("getSensorOriginalDataByID reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("response body: {} ", jsonResponse.getBody());

                JSONArray items = (JSONArray) jsonResponse.getBody().getObject().get("items");
                if (items != null && items.length() > 0) {

                    JSONObject object = items.getJSONObject(0);
                    if (object != null) {

                        String endpoint = object.getString("endp");
                        if (endpoint != null) {

                            String endp = endpoint.substring(0, endpoint.indexOf("^^"));

                            log.info("sensor endpoint : {}", endp);

                            HttpResponse<JsonNode> jsonDataResponse = Unirest.get(endp)
                                .header("Accept", "application/ld+json")
                                .header("iPlanetDirectoryPro", token)
                                .asJson();

                            if (jsonDataResponse.getStatus() == 200) {

                                result = jsonDataResponse.getBody().toString();

                                log.info("result---------------: {}", result);
                            } else {
                                log.info("error retreive sensor data with end point: {}", endp);
                            }

                        }

                    }
                }


            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = "";
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        }
        log.info("end call service");
        return result;
    }

    public Sensor getSensorInformationByID(String sensorID, String token) {

        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "\n" +
            "SELECT   ?endp\n" +
            "WHERE {\n" +
            "<" + sensorID + "> iot-lite:exposedBy ?serv ." +
            "\t?serv iot-lite:endpoint ?endp .\n" +
            "  \n" +
            "}";
        log.info("query:{}", query);

        Sensor result = new Sensor();
        try {

            log.info("start getting sensor endpoint by sensor ID: {} and token: {}", sensorID, token);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            log.info("with url:{}", platformBaseURL + "iot-registry/api/queries/execute/global");

            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/queries/execute/global")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", token)
                .body(query)
                .asJson();

            log.info("getSensorInformationByID reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("response body: {} ", jsonResponse.getBody());

                JSONArray items = (JSONArray) jsonResponse.getBody().getObject().get("items");
                if (items != null && items.length() > 0) {

                    JSONObject object = items.getJSONObject(0);
                    if (object != null) {

                        String endpoint = object.getString("endp");

                        if (endpoint != null) {

                            String endp = endpoint.substring(0, endpoint.indexOf("^^"));

                            result.setEndp(endp);

                            log.info("start get sensor data by sensor endpoint : {}", endp);


                            HttpResponse<JsonNode> jsonDataResponse = Unirest.get(endp)
                                .header("Accept", "application/ld+json")
                                .header("iPlanetDirectoryPro", token)
                                .asJson();

                            if (jsonDataResponse.getStatus() == 200) {

                                String sensorData = jsonDataResponse.getBody().toString();
                                result.setSensorData(sensorData);
                                log.info("sensor endpoint data: {}", sensorData);

                                log.info("result sensorData ---------------: {}", sensorData);
                            } else {
                                log.info("error retreive sensor data with end point: {}", endp);
                            }

                        }

                    }
                }


            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = null;
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = null;
        }
        log.info("end call service");
        return result;
    }

    private String getContent(InputStream input) {
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[1024];
        int readBytes = 0;
        try {
            while ((readBytes = input.read(b)) >= 0) {
                sb.append(new String(b, 0, readBytes, "UTF-8"));
            }
            input.close();
            return sb.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    public JSONObject getSensorDataLimitByTime(String sensor, String startDate, String endDate, String SSOtoken) {

        JSONObject userObject = null;
        try {

            String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
                "PREFIX m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
                "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
                "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dul: <http://www.loa.istc.cnr.it/ontologies/DUL.owl#>\n" +
                "PREFIX time: <http://www.w3.org/2006/time#>\n" +
                "\n" +
                "SELECT  ?sensingDevice ?dataValue ?dateTime ?observation ?sensorOutput ?quantityKind ?obsValue ?unit  ?instant\n" +
                "WHERE {\n" +
                "?observation ssn:observedBy ?sensingDevice .\n" +
                "?observation ssn:observedProperty ?obsPro.\n" +
                "?obsPro a ?quantityKind.\n" +
                "VALUES ?sensingDevice { \n" +
                "<"+sensor+ ">} .\n" +
                "?observation ssn:observationResult ?sensorOutput .\n" +
                "?sensorOutput ssn:hasValue ?obsValue .\n" +
                "?obsValue dul:hasDataValue ?dataValue .\n" +
                "?observation ssn:observationSamplingTime ?instant .\n" +
                "?instant time:inXSDDateTime ?dateTime .\n" +
                "?obsValue iot-lite:hasUnit ?u.\n" +
                "?u a ?unit.\n" +
                "  \n" +
                "}ORDER BY ?sensingDevice ASC(?dateTime)";


            log.info("query:{}", query);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            String fullUrl = platformBaseURL + "iot-registry/api/queries/execute/global?from" +startDate+"&to=" + endDate;
            log.info("querySensorDataLimitByTime fullUrl: {}", fullUrl);

            URL url = new URL(
                fullUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("iPlanetDirectoryPro", SSOtoken);
            OutputStreamWriter wr = new OutputStreamWriter(
                conn.getOutputStream());
            wr.flush();
            wr.close();
            int responseMC = conn.getResponseCode();
            if (responseMC == HttpURLConnection.HTTP_OK) {
                String security = getContent(conn.getInputStream());
                log.info("get openam user --: {}", security);
                userObject = new JSONObject(security);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userObject;
    }

    public String  querySensorDataLimitByTime(String sensor, String startDate, String endDate, String SSOtoken) {

        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX dul: <http://www.loa.istc.cnr.it/ontologies/DUL.owl#>\n" +
            "PREFIX time: <http://www.w3.org/2006/time#>\n" +
            "\n" +
            "SELECT  ?sensingDevice ?dataValue ?dateTime ?observation ?sensorOutput ?quantityKind ?obsValue ?unit  ?instant\n" +
            "WHERE {\n" +
            "?observation ssn:observedBy ?sensingDevice .\n" +
            "?observation ssn:observedProperty ?obsPro.\n" +
            "?obsPro a ?quantityKind.\n" +
            "VALUES ?sensingDevice { \n" +
            "<"+sensor+ ">} .\n" +
            "?observation ssn:observationResult ?sensorOutput .\n" +
            "?sensorOutput ssn:hasValue ?obsValue .\n" +
            "?obsValue dul:hasDataValue ?dataValue .\n" +
            "?observation ssn:observationSamplingTime ?instant .\n" +
            "?instant time:inXSDDateTime ?dateTime .\n" +
            "?obsValue iot-lite:hasUnit ?u.\n" +
            "?u a ?unit.\n" +
            "  \n" +
            "}ORDER BY ?sensingDevice ASC(?dateTime)";


        log.info("query:{}", query);

        String result = null;
        try {

            log.info("start getting sensor data by sensor ID: {} and token: {}", sensor, SSOtoken);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            String fullUrl = platformBaseURL + "iot-registry/api/queries/execute/global?from=" +startDate+"&to=" + endDate;
            log.info("querySensorDataLimitByTime fullUrl: {}", fullUrl);

            HttpResponse<String> jsonResponse = Unirest.post(fullUrl)
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .body(query).asString();


            log.info("querySensorDataLimitByTime reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {
                log.info("querySensorDataLimitByTime response body: {} ", jsonResponse.getBody());
                result = jsonResponse.getBody();
            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody());
                result = null;
            }

        } catch (UnirestException e) {
            log.error("querySensorDataLimitByTime exception error : {}", e.toString());
            e.printStackTrace();
            result = null;
        }
        log.info("end call service");
        return result;
    }

    public String querySensorDataTest(String SSOtoken) {
        String query = "Prefix ssn: <http://purl.oclc.org/NET/ssnx/ssn#> \n" +
            "Prefix iotlite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#> \n" +
            "Prefix dul: <http://www.loa.istc.cnr.it/ontologies/DUL.owl#> \n" +
            "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "Prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "Prefix time: <http://www.w3.org/2006/time#>\n" +
            "Prefix m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "Prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "Prefix iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "# select ?s (max(?ti) as ?tim) ?val ?lat ?long ?qkr\n" +
            "\n" +
            "SELECT ?time ?qkr ?val ?unit ?sensor\n" +
            "where { \n" +
            "    ?o a ssn:Observation.\n" +
            "    ?o ssn:observedBy <https://platform.fiesta-iot.eu/iot-registry/api/resources/6VC-DQQDM3VdUnaj0GfXHrcqSf_ShNzzkkWsF9D0JbgWjDIIwQS4ZsujCcGouQiQrNbbPHhm7Huj06KmSNLFnb9F1lLtGRTLLLZLJKkZGm_spsS4yk8bEh7BLCrUmlkG>.\n" +
            "   ?o ssn:observedProperty ?qk.\n" +
            "   ?qk rdf:type ?qkr.\n" +
            "   ?o ssn:observationSamplingTime ?t.\n" +
            "   ?t time:inXSDDateTime ?time.\n" +
            "   \n" +
            "   ?o ssn:observationResult ?or.\n" +
            "   ?or  ssn:hasValue ?v. \n" +
            "   ?v dul:hasDataValue ?val.\n" +
            "   \n" +
            "   ?v iot-lite:hasUnit ?u.\n" +
            "   ?u rdf:type ?unit .\n" +
            "   \n" +
            "   } limit 100";

        String result = "";
        try {

            log.info("start getting sensor data with token: {}", SSOtoken);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();


            HttpResponse<JsonNode> jsonResponse = Unirest.post( platformBaseURL + "iot-registry/api/queries/execute/global")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .body(query)
                .asJson();

            log.info("reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("response body: {} ", jsonResponse.getBody());
                result = jsonResponse.getBody().toString();
            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = "";
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        }
        log.info("end call service");
        return result;
    }

    public String getSensorData(String endp, String ssoToken) {

        String result = "";
        try {

            log.info("Start getting sensor data with endpoint: {}, and token: {}",endp, ssoToken);

            HttpResponse<String> sensorData = Unirest.get(endp)
                .header("Content-type", "text/plain")
                .header("Accept", "application/ld+json")
                .header("iPlanetDirectoryPro", ssoToken)
                .asString();

            log.info("sensor endpoint data : {}", sensorData.getBody().toString());
            result = sensorData.getBody().toString();
            log.info("sensor endpoint data: {}", result);


        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        }
        log.info("End getting sensor data with endpoint: {}, and token: {}",endp, ssoToken);
        return result;
    }

    public String getSensorMeta(String sensorId, String ssoToken) {

        String result = "";
        try {

            log.info("start getting sensor data with token: {}", ssoToken);

            HttpResponse<String> sensorMeta = Unirest.get(sensorId)
                .header("Content-type", "text/plain")
                .header("Accept", "application/ld+json")
                .header("iPlanetDirectoryPro", ssoToken)
                .asString();

            log.info("sensor endpoint data : {}", sensorMeta.getBody().toString());
            result = sensorMeta.getBody().toString();
            log.info("sensor endpoint data: {}", result);


        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        }
        log.info("end call service");
        return result;
    }

    public  static String trimSensorFromHashedId(String hashId) {

        try {
            //
            if (hashId == null || hashId.length() == 0) return hashId;

            int lastIndex = hashId.lastIndexOf("/");
            String subStringOne = hashId.substring(0, lastIndex + 1);

            String originalId = hashId.substring(lastIndex + 1, hashId.length());

            System.out.println(originalId);

            int hashLength = originalId.length();

            String trimId = originalId.substring(originalId.length() - MAX_TRIM_HASH, originalId.length());
            return  subStringOne + trimId;

        }catch (Exception ex) {
            return hashId;
        }


    }

    /*
    public static void main(String args[]) {
        String hashId = "https://platform.fiesta-iot.eu/iot-registry/api/resources/tRRAK2lA6S5GEca2qPQD6hWzOn-kLp82OXHnXItm16LbPlSitapxvtgEcrxPmWuDG-vqcW8xUTwrYj13_jt-t01DzPKZA6v1VYA_UVR77ihfGV9LONi8Tm0Ccv3rzBXR";
        System.out.println(trimSensorFromHashedId(hashId));

    }*/

    public List<Sensor> getSensorsByQuantityKind(String quantityKind, String token) {
        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "Prefix m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "\n" +
            "SELECT ?sensor ?type ?qk ?unit ?endp ?lat ?long   \n" +
            "WHERE {\n" +
            "\t\n" +
            "    \t?dev ssn:onPlatform ?platform .\n" +
            "    \t?platform geo:location ?point .\n" +
            "    \t?point geo:lat ?lat .\n" +
            "    \t?point geo:long ?long .\n" +
            "    \t?dev ssn:hasSubSystem ?sensor .\n" +
            "    \t?sensor iot-lite:hasQuantityKind ?qkr .\n" +
            "    \t?qkr rdf:type m3-lite:"+ quantityKind +" .\n" +
            "    \t?sensor iot-lite:hasUnit ?unitr .\n" +
            "    \t?unitr rdf:type ?unit .\n" +
            "    \tOPTIONAL {\n" +
            "    \t\t?sensor iot-lite:exposedBy ?serv .\n" +
            "    \t\t?serv iot-lite:endpoint ?endp . \n" +
            "\t\t}\n" +
            "}";

        List<Sensor> result = null;
        try {

            log.info("start getting sensors by token: {}", token);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            log.info("platformBaseURL: {}", platformBaseURL);

            Unirest.setTimeouts(0,0);
            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/queries/execute/global")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", token)
                .body(query)
                .asJson();

            log.info("reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("success call service with response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody());

                JSONObject bodyObject = null;
                if(jsonResponse.getBody().toString().contains("entity")) {
                    String body = (String) jsonResponse.getBody().getObject().get("entity");

                    bodyObject = new JSONObject(body);

                } else {
                    bodyObject = jsonResponse.getBody().getObject();
                }

                log.info("bodyObject: {}", bodyObject);
                JSONArray items = bodyObject.getJSONArray("items");
                if (items != null && items.length() > 0) {
                    result = new ArrayList<>();

                    int lengh = items.length();
                    for (int i = 0; i < lengh; i++) {
                        JSONObject object = items.getJSONObject(i);

                        //log.info("object--------------------: {}", object);

                        String endpoint = null;
                        try {
                            endpoint = object.getString("endp");
                        } catch (Exception exs) {

                        }
                        if (endpoint != null) {

                            String endp = endpoint.substring(0, endpoint.indexOf("^^"));
                            String sensorId = object.get("sensor").toString();
                            String unit = object.get("unit").toString();
                            String qk = "http://purl.org/iot/vocab/m3-lite#"+ quantityKind; //object.get("qk").toString();
                            String shortQk = quantityKind;
                            String shortUnit = unit.replace("http://purl.org/iot/vocab/m3-lite#","");
                            String type = "";//object.get("type").toString();
                            String sensorHahsedId = object.get("sensor").toString();
                            String longStr = object.get("long").toString();
                            String latStr = object.get("lat").toString();
                            //String originalID = getSensorOriginalIDByHashedId(sensorId, token);

                            String trimSensorID = trimSensorFromHashedId(sensorHahsedId);

                            float lng = Float.parseFloat(longStr.substring(0, longStr.indexOf("^^")));
                            float lat = Float.parseFloat(latStr.substring(0, latStr.indexOf("^^")));

                            Sensor sensor = new Sensor(endp, unit, qk, shortQk, shortUnit, sensorHahsedId, sensorHahsedId, sensorHahsedId,type, lng, lat);
                            result.add(sensor);
                        }
                    }
                }

            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = null;
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = null;
        }
        log.info("end call service");
        return result;
    }


//    public static void main(String args[]) {
//        String endp = "https://platform.fiesta-iot.eu/iot-registry/api/endpoints/xo5MlBGlvPxt2xe7wNeBj8dcYsu5HB91mNGRn6ES5CZG3bJr5ahel4hnohiWU5_mdNjjI_AYdiUlbj97O_X4kyeAqXM8FNbf00K38FELbAfvtCQJVawHmrxyzRCX2hhG";
//        String token = "AQIC5wM2LY4Sfcw1K2DV7-y9vXJQFMSJ2lWUk19uyJ-dYxA.*AAJTSQACMDEAAlNLABM1ODA0MTM2Mjk4NzEyMTMzNTg3AAJTMQAA*";
//        try {
//            HttpResponse<JsonNode> jsonDataResponse = Unirest.get(endp)
//                .header("Accept", "application/ld+json")
//                .header("iPlanetDirectoryPro", token)
//                .asJson();
//
//            if (jsonDataResponse.getStatus() == 200) {
//
//                String sensorData = jsonDataResponse.getBody().toString();
//                //result.setSensorData(sensorData);
//                System.out.println("sensor endpoint data: " + sensorData);
//
//
//            } else {
//
//
//            }
//        }catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

//    public static void main(String args[]) {
//        System.out.print(new Date().toString());
//        System.out.print(new Date().toInstant().toString());
//        String sensorId = "https://platform.fiesta-iot.eu/iot-registry/api/resources/VsnDY_ipIeAhy2eCc5jxNRqGyBVsIwso2bO-8KCr7GKnfKLgda8TdXItkjaADUHLb6VnxSxvR7MddDzbM9fR-Crr9BuuRehd9QCZYPKVzsuaAvFxz6BhRc_PTWFEzu2I";
//        String token = "AQIC5wM2LY4SfcwPM0AhRwmqvx1G9_6Z3mpGKBV4ZE-NtuE.*AAJTSQACMDEAAlNLABQtMjg2NDg1MzExNzE0MzEzNTgzNAACUzEAAA..*";
//        String result = new TestbedClientService().querySensorDataLimitByTime(sensorId,"2018-07-06","2018-07-07", token);
//        System.out.print(result);
//
//    }
}
