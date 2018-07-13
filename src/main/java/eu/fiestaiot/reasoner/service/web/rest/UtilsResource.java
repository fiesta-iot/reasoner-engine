package eu.fiestaiot.reasoner.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.reasoner.service.config.FiestaProperties;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UtilsResource {

    private final Logger log = LoggerFactory.getLogger(UtilsResource.class);

    @Inject
    private FiestaProperties fiestaTestbedProperties;

    @Value(value = "classpath:apidocs.json")
    private Resource apiDocResource;
    private final List<String> quantityKindList = new ArrayList<String>(Arrays.asList(
            "Acceleration",
            "Acceleration Instantaneous",
            "AirPollution",
            "AirQuality",
            "AlcoholLevel",
            "Angular",
            "BatteryLevel",
            "BloodGlucose",
            "Calcium",
            "Capacitance",
            "ChemicalAgentAtmosphericConcentration",
            "Chemical Agent Atmospheric Concentration Air Particles",
            "Chemical Agent Atmospheric Concentration CO",
            "Chemical Agent Atmospheric Concentration NO2",
            "Chemical Agent Atmospheric Concentration O3",
            "Cholesterol",
            "CloudCover",
            "CO2",
            "Communication",
            "Conductivity",
            "DewPoint",
            "Delta Dew Point",
            "Dew Point Temperature",
            "Direction",
            "Direction Azimuth",
            "Direction Heading",
            "Distance",
            "ElectricCharge",
            "ElectricCurrent",
            "ElectricField",
            "Electric Field 1800 MHz",
            "Electric Field 2100 MHz",
            "Electric Field 2400 MHz",
            "Electric Field 900 MHz",
            "ElectricPotential",
            "ElectricalResistance",
            "Energy",
            "FillLevel",
            "Fill Level Gas Tank",
            "Fill Level Gas Tank 1",
            "Fill Level Gas Tank 2",
            "Fill Level Waste Container",
            "Frequency",
            "FuelConsumption",
            "Fuel Consumption Instantaneous",
            "Fuel Consumption Total",
            "HeartBeat",
            "Humidity",
            "Illuminance",
            "Weather Luminosity",
            "LeafWetness",
            "Location",
            "LuminousFlux",
            "LuminousIntensity",
            "MagneticField",
            "MagneticFluxDensity",
            "Mass",
            "Mileage",
            "Mileage Distance To Service",
            "Mileage Total",
            "Motion",
            "MotionState",
            "Motion State Vehicle",
            "NumberStep",
            "PH",
            "Position",
            "Position Altitude",
            "Position Latitude",
            "Position Longitude",
            "Potassium",
            "Power",
            "ActivePower",
            "Reactive Power",
            "Precipitation",
            "Weather Precipitation",
            "Presence",
            "PresenceState",
            "Presence State Driver Card",
            "Presence State Driver Card 1",
            "Presence State Driver Card 2",
            "Presence State Emergency Vehicle",
            "Presence State Parking",
            "Presence State People",
            "Pressure",
            "Atmospheric Pressure",
            "Blood pressure",
            "Diastolic Blood pressure",
            "Systolic Blood pressure",
            "Proximity",
            "Rainfall",
            "RecognizedActivity",
            "RelativeHumidity",
            "RFIDQuantityKind",
            "RFID Measurement Type",
            "Animal",
            "Book",
            "CD",
            "Clothes",
            "DVD",
            "Food",
            "Luggage",
            "ParkingSpace",
            "Passport",
            "Payment Card",
            "Toll",
            "TransitPass",
            "RoadOccupancy",
            "RotationalSpeed",
            "Rotational Speed",
            "Rotational Speed Engine",
            "Salinity",
            "SkinConductance",
            "Sodium",
            "SoilHumidity",
            "SoilMoistureTension",
            "SolarRadiation",
            "SoundPressureLevel",
            "Sound Pressure Level",
            "Sound Pressure Level Ambient",
            "Sound",
            "Speed",
            "Speed Average",
            "Speed Instantaneous",
            "Speed Median",
            "SPO2",
            "SunPositionDirection",
            "SunPositionElevation",
            "Temperature",
            "Air Temperature, Weather Temperature, Ambient Temperature",
            "Board Temperature",
            "Body Temperature",
            "Building/Room Temperature",
            "Room Temperature",
            "Food Temperature",
            "Household Appliance Temperature",
            "Road Temperature",
            "Soil Temperature",
            "Temperature Engine",
            "Waste Container",
            "Water Temperature",
            "TimeRelatedState",
            "Time Related State",
            "Time Related State Driver",
            "Time Related State Driver 1",
            "Time Related State Driver 2",
            "Timestamp",
            "TrafficCongestion",
            "TrafficIntensity",
            "VehicleOverspeedState",
            "Visibility",
            "WaterLevel",
            "Weight",
            "WindChill",
            "WindDirection",
            "WindSpeed",
            "WorkingState",
            "Working State",
            "Working State Driver",
            "Working State Driver 1",
            "Working State Driver 2",
            "Others"));
    private final List<String> unitofMeasurementList = new ArrayList<String>(Arrays.asList(
            "Altitude",
            "Ampere (A)",
            "Microampere (uA)",
            "Milliampere (mA)",
            "Bar",
            "Centibar",
            "Millibar",
            "BeatPerMinute",
            "Candela",
            "Coulomb",
            "Day",
            "Decibel (db)",
            "Degree",
            "Degree angle",
            "Degree celsius",
            "Degree fahrenheit",
            "Dimensionless",
            "EAQI",
            "Farad",
            "Gauss",
            "Gram (g)",
            "Kilogram (kg)",
            "Microgram (ug)",
            "Milligram (mg)",
            "GramPerCubicMetre",
            "KilogramPerCubicMetre, KilogramPerCubicMeter",
            "MicrogramPerCubicMetre, MicrogramPerCubicMeter",
            "MilligramPerCubicMetre, MilligramPerCubicMeter",
            "GramPerLiter",
            "Hertz",
            "Hour",
            "Inch",
            "Index",
            "Kelvin",
            "Kilo",
            "KiloWattHour",
            "KilobitsPerSecond",
            "Latitude",
            "Liter",
            "Liter, Litre",
            "Millilitre",
            "LitrePer100Kilometres",
            "Longitude",
            "Lumen",
            "Lux",
            "Meter",
            "Centimetre, Centimeter",
            "Kilometre, Kilometer",
            "Millimetre, millimeter",
            "MeterPerSecond",
            "Meter Per Second (m/s)",
            "Kilometer Per Hour",
            "MeterPerSecondSquare",
            "Miles",
            "MilligramPerSquareMetre",
            "MillimeterPerHour",
            "Millisecond",
            "MillivoltPerMeter",
            "MinuteAngle",
            "MinuteTime",
            "MmHg",
            "MmolPerLiter",
            "Ohm",
            "Okta",
            "Pascal",
            "Percent",
            "Pound",
            "PPM",
            "Radian",
            "RadianPerSecond",
            "RevolutionsPerMinute",
            "Scale",
            "SecondAngle",
            "SecondTime",
            "Tesla",
            "Time",
            "Tonne",
            "VehiclesPerMinute",
            "Volt",
            "Microvolt (uV)",
            "Millivolt (mV)",
            "VoltAmpereReactive",
            "Wout",
            "Watt (W)",
            "Microwatt (uW)",
            "Milliwatt (mW)",
            "WattPerMeterSquare",
            "WattPerSquareMeter",
            "Year",
            "Others"));

    private final List<String> contentTypes = new ArrayList<String>(Arrays.asList(
            "application/ld+json",
            "application/n-triples",
            "application/n3",
            "text/n-quads",
            "application/rdf+json",
            "application/rdf+xml",
            "application/trig",
            "application/trix",
            "application/turtle",
            "text/turtle",
            "application/x-binary-rdf"));

    @GetMapping("/quantityKinds")
    @Timed
    public ResponseEntity<List<String>> getAllQuantityKind() {

        List<String> listQKs = new ArrayList<String>();
        try {
            OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
            InputStream in = null;
            log.info("-- Endpoint --- :{}", fiestaTestbedProperties.getEnpoints().getOntologyM3LiteUrl());
            in = new URL(fiestaTestbedProperties.getEnpoints().getOntologyM3LiteUrl()).openStream();
            ontoModel.read(in, null);
            in.close();

            String query2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                    + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                    + "PREFIX qu: <http://purl.org/NET/ssnx/qu/qu#> "
                    + "SELECT * WHERE { ?s rdfs:subClassOf* qu:QuantityKind .}";

            Query query = QueryFactory.create(query2);

            QueryExecution qe = QueryExecutionFactory.create(query, ontoModel);

            ResultSet r = qe.execSelect();
            while (r.hasNext()) {
                QuerySolution thisRow = r.next();
                listQKs.add(thisRow.get("s").toString().replace("http://purl.org/iot/vocab/m3-lite#", ""));
            }

            //TODO: add code to remove base case from the listQKs http://purl.org/NET/ssnx/qu/qu#QuantityKind the above code will also return the base class
            listQKs.remove(0);
            qe.close();
            ontoModel.close();
        } catch (JenaException je) {
            log.error("ERROR", je.getMessage());
        } catch (IOException e) {
            log.error("ERROR", e.getMessage());
        } catch (Exception e) {
            log.error("ERROR", e.getMessage());
        }
        //return ;
        //}
        return new ResponseEntity<>(listQKs, null, HttpStatus.OK);
    }

    @GetMapping("/m3QuantityKinds")
    @Timed
    public ResponseEntity<List<String>> getQuantityKind() {

        return new ResponseEntity<>(quantityKindList, null, HttpStatus.OK);
    }

    @GetMapping("/uoms")
    @Timed
    public ResponseEntity<List<String>> getAllUnitofMesurements() {

        return new ResponseEntity<>(unitofMeasurementList, null, HttpStatus.OK);
    }

    @GetMapping("/contentTypes")
    @Timed
    public ResponseEntity<List<String>> getAllContentTypes() {
        return new ResponseEntity<>(contentTypes, null, HttpStatus.OK);
    }

    @GetMapping("/docs")
    @Timed
    public String getApiDocs() {
        try {
            log.info("get api is:{} ", apiDocResource.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(apiDocResource.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            reader.close();

            return stringBuilder.toString();

        } catch (IOException ex) {
            log.error("get api docs ex:" + ex.toString());
            return null;
        }
    }

}
