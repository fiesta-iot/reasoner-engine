package eu.fiestaiot.reasoner.service.service.impl;

import eu.fiestaiot.reasoner.service.domain.Execution;
import eu.fiestaiot.reasoner.service.domain.RegisterRule;
import eu.fiestaiot.reasoner.service.repository.RegisterRuleRepository;
import eu.fiestaiot.reasoner.service.service.RegisterRuleService;
import eu.fiestaiot.reasoner.service.service.TestbedClientService;
import eu.fiestaiot.reasoner.service.service.dto.SensorData;
import eu.fiestaiot.reasoner.service.service.util.ParserUtil;
import eu.fiestaiot.reasoner.service.web.rest.vm.ReExecuteRule;
import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.io.StringWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing RegisterRule.
 */
@Service
@Transactional
public class RegisterRuleServiceImpl implements RegisterRuleService{

    private final Logger log = LoggerFactory.getLogger(RegisterRuleServiceImpl.class);

    @Inject
    private RegisterRuleRepository registerRuleRepository;

    @Inject
    private TestbedClientService testbedClientService;

    @Inject
    private ParserUtil parserUtil;
    /**
     * Save a registerRule.
     *
     * @param registerRule the entity to save
     * @return the persisted entity
     */
    public RegisterRule save(RegisterRule registerRule) {
        log.debug("Request to save RegisterRule : {}", registerRule);
        RegisterRule result = registerRuleRepository.save(registerRule);
        return result;
    }

    /**
     *  Get all the registerRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RegisterRule> findAll(Pageable pageable) {
        log.debug("Request to get all RegisterRules");
        Page<RegisterRule> result = registerRuleRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one registerRule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RegisterRule findOne(Long id) {
        log.debug("Request to get RegisterRule : {}", id);
        RegisterRule registerRule = registerRuleRepository.findOne(id);
        return registerRule;
    }

    /**
     *  Delete the  registerRule by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RegisterRule : {}", id);
        registerRuleRepository.delete(id);
    }


/*
    public static void main(String args[]) {
        RegisterRule result = new RegisterRuleServiceImpl().excuteReasoning();
        System.out.println(result.getData());
        System.out.println(result.getFullData());
        System.out.println(result.getInferredData());


    } */

//
//   public static void main(String args[]) {
//       new RegisterRuleServiceImpl().excuteReasoning();
//
//   }
    public  RegisterRule excuteReasoning() {
        RegisterRule registerRule = new RegisterRule();



        try {

//            FileUtils.writeStringToFile(new File("data.jsonld"), registerRule.getData(), "UTF-8", false);
//
//            FileUtils.writeStringToFile(new File("rule.rules"), registerRule.getRuleContent(), "UTF-8", false);
//
//            FileUtils.writeStringToFile(new File("new_data.jsonld"), "", "UTF-8", false);


            Model model = RDFDataMgr.loadModel("data.jsonld", Lang.JSONLD);
            List<Rule> rules = Rule.rulesFromURL("rule.rules");
            log.info("--------rule--------: {}", rules.toString());
            Reasoner engine = new GenericRuleReasoner(rules);
            InfModel inf = ModelFactory.createInfModel(engine, model);

            StringWriter fullInfferedWriter = new StringWriter();

            RDFDataMgr.write(fullInfferedWriter, inf, RDFFormat.JSONLD);

            log.info("setFullData--------------:{}", fullInfferedWriter.toString());

            registerRule.setFullData(fullInfferedWriter.toString());

            StringWriter differenceInfferedWriter = new StringWriter();

            Model entails = ModelFactory.createDefaultModel();
            entails = inf.difference(model);
            RDFDataMgr.write(differenceInfferedWriter, entails, RDFFormat.JSONLD);

            registerRule.setInferredData(differenceInfferedWriter.toString());

            log.info("setInferredData--------------:{}", differenceInfferedWriter.toString());

            return registerRule;

        } catch (Exception ex) {
            log.info("Error: {}", ex.toString());
            return null;
        }
    }


    @Override
    public RegisterRule executeReasoning(RegisterRule registerRule) {

            try {

                log.info("Start execute rule on sensor: {}", registerRule.getSensorEndp());


                FileUtils.writeStringToFile(new File("data.jsonld"), registerRule.getData(), "UTF-8", false);

                FileUtils.writeStringToFile(new File("rule.rules"), registerRule.getRuleContent(), "UTF-8", false);

                FileUtils.writeStringToFile(new File("new_data.jsonld"), "", "UTF-8", false);


                Model model = RDFDataMgr.loadModel("data.jsonld", Lang.JSONLD);
                List<Rule> rules = Rule.rulesFromURL("rule.rules");
                log.info("With rule content: {}", rules.toString());
                Reasoner engine = new GenericRuleReasoner(rules);
                InfModel inf = ModelFactory.createInfModel(engine, model);

                StringWriter fullInfferedWriter = new StringWriter();

                RDFDataMgr.write(fullInfferedWriter, inf, RDFFormat.JSONLD);

                log.info("setFullData--------------:{}", fullInfferedWriter.toString());

                registerRule.setFullData(fullInfferedWriter.toString());

                StringWriter differenceInfferedWriter = new StringWriter();

                Model entails = ModelFactory.createDefaultModel();
                entails = inf.difference(model);
                RDFDataMgr.write(differenceInfferedWriter, entails, RDFFormat.JSONLD);

                registerRule.setInferredData(differenceInfferedWriter.toString());
                registerRule.setData(registerRule.getData());

                log.info("setInferredData--------------:{}", differenceInfferedWriter.toString());

                return registerRule;

            } catch (Exception ex) {
                ex.printStackTrace();
                log.info("Error: {}", ex.toString());
                return null;
            }
    }

    public static  String InstantToString(Instant date) {
        LocalDateTime ldt = LocalDateTime.ofInstant(date, ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String formatDateTime = ldt.format(formatter);
        return formatDateTime;

        //String dateTime = ldt.getYear() + "" + ldt.getMonthValue() + "" + ldt.getDayOfMonth() + "" + ldt.getHour() + "" + ldt.getMinute() + ldt.getSecond();
        //return  dateTime;
    }
//    public static void main(String args[]) {
//        System.out.println(RegisterRuleServiceImpl.InstantToString(Instant.now()));
//    }


    @Override
    public Execution excuteReasoningWithTime(ReExecuteRule reExecuteRule, String SSOToken, String userId) {
        Execution result = null;
        RegisterRule registerRule = registerRuleRepository.findOne(reExecuteRule.getId());
        if(registerRule == null) {
            return result;
        }

        try {

            result = new Execution();
            result.setStarted(new Date());

            log.info("start query sensor data with params, sensor id: {}, start_time: {}, end_time: {}", registerRule.getSensor(),reExecuteRule.getStartTime().toString(),reExecuteRule.getEndTime().toString());

            String startDate = InstantToString(reExecuteRule.getStartTime());
            String endDate = InstantToString(reExecuteRule.getEndTime());

            String listSensorData = testbedClientService.querySensorDataLimitByTime(registerRule.getSensor(),startDate, endDate, SSOToken);

            log.info("result sensor listSensorData: {}", listSensorData);


            //log.info("start parse sensor data to list");
            List<SensorData> sensorDataList = parserUtil.parseListSensors(listSensorData);

            log.info("list sensor data: {}", sensorDataList);


            log.info("start get sensor endpoint data: {}", registerRule.getSensorEndp());
            String originalData = testbedClientService.getSensorData(registerRule.getSensorEndp(), SSOToken);


            log.info("result get sensor endpoint data final: {}", originalData);


            String finalInputDataSet = parserUtil.parserSensorData(originalData, sensorDataList,registerRule);


            log.info("finalInputDataSet----------------------------: {}", finalInputDataSet);


            FileUtils.writeStringToFile(new File("data.jsonld"), finalInputDataSet, "UTF-8", false);

            FileUtils.writeStringToFile(new File("rule.rules"), registerRule.getRuleContent(), "UTF-8", false);

            FileUtils.writeStringToFile(new File("new_data.jsonld"), "", "UTF-8", false);


            Model model = RDFDataMgr.loadModel("data.jsonld", Lang.JSONLD);

            log.info("--------model---------------------------: {}", model.toString());

            List<Rule> rules = Rule.rulesFromURL("rule.rules");
            log.info("--------rule----------------------------: {}", rules.toString());
            Reasoner engine = new GenericRuleReasoner(rules);
            InfModel inf = ModelFactory.createInfModel(engine, model);

            StringWriter fullInfferedWriter = new StringWriter();

            RDFDataMgr.write(fullInfferedWriter, inf, RDFFormat.JSONLD);

            log.info("setFullData--------------:{}", fullInfferedWriter.toString());

            registerRule.setFullData(fullInfferedWriter.toString());

            StringWriter differenceInfferedWriter = new StringWriter();

            Model entails = ModelFactory.createDefaultModel();
            entails = inf.difference(model);
            RDFDataMgr.write(differenceInfferedWriter, entails, RDFFormat.JSONLD);

            registerRule.setInferredData(differenceInfferedWriter.toString());

            log.info("setInferredData--------------:{}", differenceInfferedWriter.toString());

            result.setRegisterRule(registerRule);
            result.setUserId(userId);
            result.setSensor(registerRule.getSensor());
            result.setRuleContent(registerRule.getRuleContent());
            result.setFullData(fullInfferedWriter.toString());
            result.setOriginalData(finalInputDataSet);
            result.setInfferedData(differenceInfferedWriter.toString());
            result.setStatus(true);
            result.setCreated(new Date());
            result.setEnded(new Date());
            result.setType(2);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("Error: {}", ex.toString());
            result.setStatus(false);
        }

        return result;
    }

    @Override
    public Page<RegisterRule> findAllByUserId(String userID, Pageable pageable) {
        return registerRuleRepository.findAllByUserId(userID, pageable);
    }
}
