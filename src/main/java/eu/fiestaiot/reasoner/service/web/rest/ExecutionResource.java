package eu.fiestaiot.reasoner.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.reasoner.service.domain.Execution;
import eu.fiestaiot.reasoner.service.domain.RegisterRule;
import eu.fiestaiot.reasoner.service.service.ExecutionService;
import eu.fiestaiot.reasoner.service.service.OpenAMSecurityHelper;
import eu.fiestaiot.reasoner.service.service.RegisterRuleService;
import eu.fiestaiot.reasoner.service.service.TestbedClientService;
import eu.fiestaiot.reasoner.service.service.dto.Sensor;
import eu.fiestaiot.reasoner.service.service.mapper.ExecutionMapper;
import eu.fiestaiot.reasoner.service.web.rest.util.HeaderUtil;
import eu.fiestaiot.reasoner.service.web.rest.util.PaginationUtil;

import eu.fiestaiot.reasoner.service.web.rest.vm.ReExecuteRule;
import eu.fiestaiot.reasoner.service.web.rest.vm.dto.ExecutionDTO;
import eu.fiestaiot.reasoner.service.web.rest.vm.request.ExecuteRuleRequest;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Execution.
 */
@RestController
@RequestMapping("/api")
public class ExecutionResource {

    private final Logger log = LoggerFactory.getLogger(ExecutionResource.class);

    @Inject
    private ExecutionService excutionService;

    @Inject
    private RegisterRuleService registerRuleService;

    @Inject
    private OpenAMSecurityHelper openAMSecurityHelper;

    @Inject
    private TestbedClientService testbedClientService;

    @Inject
    private ExecutionMapper executionMapper;

    /**
     * POST  /excutions : Create a new execution.
     *
     * @param execution the execution to create
     * @return the ResponseEntity with status 201 (Created) and with body the new execution, or with status 400 (Bad Request) if the execution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/executions")
    @Timed
    public ResponseEntity<ExecutionDTO> createExecution(@Valid @RequestBody ExecuteRuleRequest execution, HttpServletRequest request) throws URISyntaxException {

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        log.info("REST request to save Execution : {}", execution);

        RegisterRule registerRule = registerRuleService.findOne(execution.getRegisterRuleId());

        if(registerRule == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("execution", "message", "Not found any register rule by given register ID")).body(null);
        }


        Execution result = new Execution();
        result.setUserId(userID);
        result.setRegisterRule(registerRule);

        //set start time

        if(execution.getExecuteType() == 1) {
            result.setStarted(new Date());
            result.setEnded(new Date());

            log.info("Start getting current sensor data with endpoint: {}", registerRule.getSensorEndp());
            Sensor sensoObject = testbedClientService.getSensorInformationByID(registerRule.getSensor(), token);
            if(sensoObject == null || sensoObject.getSensorData() == null) {
                result.setStatus(false);
                result.setType(1);
                result.setRuleContent(registerRule.getRuleContent());
                result.setSensor(registerRule.getSensor());
                result.setRegisterRule(registerRule);
                result.setCreated(new Date());
                result = excutionService.save(result);
                ExecutionDTO dto = executionMapper.executionToExecutionDTO(result);
                return ResponseEntity.created(new URI("/api/excutions/" + dto.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("execution", dto.getId().toString()))
                    .body(dto);
            }

            log.info("Current sensor data: {}", sensoObject.getSensorData());
            registerRule.setData(sensoObject.getSensorData());

            RegisterRule excuteRule = registerRuleService.executeReasoning(registerRule);

            if(excuteRule != null) {
                result.setType(1);
                result.setStatus(true);
                result.setCreated(new Date());
                result.setRuleContent(registerRule.getRuleContent());
                result.setSensor(registerRule.getSensor());
                result.setFullData(excuteRule.getFullData());
                result.setInfferedData(excuteRule.getInferredData());
                result.setOriginalData(excuteRule.getData());
                result.setRegisterRule(registerRule);


            } else {
                // registerRuleResult = registerRuleService.save(registerRule);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("execution", "idexists", "System can not execute rule, please check your input data and try again!")).body(null);
            }

            execution.setExecuteType(1);
            result = excutionService.save(result);
            ExecutionDTO dto = executionMapper.executionToExecutionDTO(result);

            return ResponseEntity.created(new URI("/api/excutions/" + dto.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("execution", dto.getId().toString()))
                .body(dto);

        } else {

            ReExecuteRule reExecuteRule = new ReExecuteRule();
            reExecuteRule.setId(registerRule.getId());
            reExecuteRule.setEndTime(execution.getEnded().toInstant());
            reExecuteRule.setStartTime(execution.getStarted().toInstant());

            long days = Math.abs(Duration.between(execution.getEnded().toInstant(), execution.getStarted().toInstant()).toHours());

            log.info("calculate started and ended : {}", days);

            if(execution.getStarted().after(execution.getEnded() ) || days > 6) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("execution", "message", "Started time can not greater than ended time! Please change input started time and ended time and try again!")).body(null);
            }

            log.info("Start execute register rule: {}, with started time: {}, ended time: {}", reExecuteRule.getId(), reExecuteRule.getStartTime().toString(), reExecuteRule.getEndTime().toString());


            Execution executionResult = registerRuleService.excuteReasoningWithTime(reExecuteRule, token, userID);

            if (executionResult  ==  null || executionResult.getStatus() != true) {
                log.info("Execute rule fail!");
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("execution", "message", "System can not execute rule, please check your input data and try again!")).body(null);
            }

            executionResult.setType(execution.getExecuteType());
            result = excutionService.save(executionResult);

            ExecutionDTO dto = executionMapper.executionToExecutionDTO(result);

            return ResponseEntity.created(new URI("/api/excutions/" + dto.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("execution", dto.getId().toString()))
                .body(dto);
        }
    }
    /**
     * GET  /excutions : get all the excutions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of excutions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/executions")
    @Timed
    public ResponseEntity<List<ExecutionDTO>> getAllExecutions(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of Excutions");
        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);

        Page<Execution> results = excutionService.findAllByUserId(userID, pageable);

        Page<ExecutionDTO> page = results.map(execution -> executionMapper.executionToExecutionDTO(execution));


        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/executions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /excutions/:id : get the "id" excution.
     *
     * @param id the id of the excution to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the excution, or with status 404 (Not Found)
     */
    @GetMapping("/executions/{id}")
    @Timed
    public ResponseEntity<ExecutionDTO> getExecution(@PathVariable Long id) {
        log.debug("REST request to get Execution : {}", id);
        Execution execution = excutionService.findOne(id);
        ExecutionDTO dto = executionMapper.executionToExecutionDTO(execution);
        return Optional.ofNullable(dto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /excutions/:id : delete the "id" excution.
     *
     * @param id the id of the excution to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    /*
    @DeleteMapping("/executions/{id}")
    @Timed
    public ResponseEntity<Void> deleteExecution(@PathVariable Long id) {
        log.debug("REST request to delete executions : {}", id);
        excutionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("excution", id.toString())).build();
    }*/

}
