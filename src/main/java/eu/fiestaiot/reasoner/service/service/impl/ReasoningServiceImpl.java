package eu.fiestaiot.reasoner.service.service.impl;

import eu.fiestaiot.reasoner.service.service.ReasoningService;
import eu.fiestaiot.reasoner.service.domain.Reasoning;
import eu.fiestaiot.reasoner.service.repository.ReasoningRepository;
import eu.fiestaiot.reasoner.service.service.TestbedClientService;
import eu.fiestaiot.reasoner.service.service.dto.Sensor;
import eu.fiestaiot.reasoner.service.service.dto.ValidateRuleRequest;
import eu.fiestaiot.reasoner.service.service.dto.ValidateRuleResponse;
import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Service Implementation for managing Reasoning.
 */
@Service
@Transactional
public class ReasoningServiceImpl implements ReasoningService {

    private final Logger log = LoggerFactory.getLogger(ReasoningServiceImpl.class);

    @Inject
    private ReasoningRepository reasoningRepository;

    @Inject
    private TestbedClientService testbedClientService;

    /**
     * Save a reasoning.
     *
     * @param reasoning the entity to save
     * @return the persisted entity
     */
    public Reasoning save(Reasoning reasoning) {
        log.debug("Request to save Reasoning : {}", reasoning);
        Reasoning result = reasoningRepository.save(reasoning);
        return result;
    }

    /**
     * Get all the reasonings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Reasoning> findAll(Pageable pageable) {
        log.debug("Request to get all Reasonings");
        Page<Reasoning> result = reasoningRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one reasoning by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Reasoning findOne(Long id) {
        log.debug("Request to get Reasoning : {}", id);
        Reasoning reasoning = reasoningRepository.findOne(id);
        return reasoning;
    }

    /**
     * Delete the  reasoning by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reasoning : {}", id);
        reasoningRepository.delete(id);
    }

    @Override
    public ValidateRuleResponse validateRule(ValidateRuleRequest request, String token) {
        String sensor = request.getSensorId();
        String ruleContent = request.getRule();

        ValidateRuleResponse response = new ValidateRuleResponse();
        Sensor sensorObject = testbedClientService.getSensorInformationByID(sensor, token);
        if(sensorObject == null || sensorObject.getEndp() == null || sensorObject.getSensorData() == null) {
            response.setMessage("Can not get sensor information given by sensor ID:" + sensor);
            response.setResult(false);
            return response;
        }
       // String sensorData = testbedClientService.getSensorData(sensorObject.getEndp(), token);

        log.info(" validateRule get sensor data: {}", sensorObject.getSensorData());
        if (sensorObject.getSensorData() == null) {
            response.setMessage("Error rule validation!, could not get sensor data !");
            response.setResult(false);
            return response;
        }

        try {

            FileUtils.writeStringToFile(new File("validate_data.jsonld"), sensorObject.getSensorData(), "UTF-8", false);

            FileUtils.writeStringToFile(new File("validate_rule.rules"), ruleContent, "UTF-8", false);

            Model model = RDFDataMgr.loadModel("validate_data.jsonld", Lang.JSONLD);
            List<Rule> rules = Rule.rulesFromURL("validate_rule.rules");
            log.info("--------rule--------: {}", rules.toString());
            Reasoner engine = new GenericRuleReasoner(rules);
            InfModel inf = ModelFactory.createInfModel(engine, model);

            ValidityReport validity = inf.validate();
            if (validity.isValid()) {
                System.out.println("OK");
                response.setMessage("Rule validation is valid!");
                response.setResult(true);

                log.info("Rule validation is passed");
            } else {
                System.out.println("Conflicts");
                log.info("Rule validation is conflicts");
                StringBuffer str = new StringBuffer();
                for (Iterator i = validity.getReports(); i.hasNext(); ) {
                    log.info(" - " + i.next());
                    str.append("-" + i.next());
                }

                response.setMessage(str.toString());
                response.setResult(false);
            }
            return response;

        } catch (Exception ex) {
            log.info("Error rule validation: {}", ex.toString());
            response.setResult(false);
            response.setMessage(ex.toString());
            return response;
        }

    }


}
