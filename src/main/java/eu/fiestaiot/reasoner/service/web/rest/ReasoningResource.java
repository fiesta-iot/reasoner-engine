package eu.fiestaiot.reasoner.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.fiestaiot.reasoner.service.domain.Reasoning;
import eu.fiestaiot.reasoner.service.domain.RegisterRuleSummary;
import eu.fiestaiot.reasoner.service.service.OpenAMSecurityHelper;
import eu.fiestaiot.reasoner.service.service.ReasoningService;
import eu.fiestaiot.reasoner.service.service.TestbedClientService;
import eu.fiestaiot.reasoner.service.service.dto.*;
import eu.fiestaiot.reasoner.service.service.mapper.ReasoningMapper;
import eu.fiestaiot.reasoner.service.service.util.RuleUtils;
import eu.fiestaiot.reasoner.service.web.rest.util.HeaderUtil;
import eu.fiestaiot.reasoner.service.web.rest.util.PaginationUtil;

import eu.fiestaiot.reasoner.service.web.rest.vm.ReasoningStatistic;
import eu.fiestaiot.reasoner.service.web.rest.vm.ReasoningStatisticResponse;
import eu.fiestaiot.reasoner.service.web.rest.vm.dto.ReasoningDTO;
import eu.fiestaiot.reasoner.service.web.rest.vm.request.CreateRuleWithExpertRequest;
import eu.fiestaiot.reasoner.service.web.rest.vm.request.UpdateRuleWithExpertRequest;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.*;

/**
 * REST controller for managing Reasoning.
 */
@RestController
@RequestMapping("/api")
public class ReasoningResource {

    private final Logger log = LoggerFactory.getLogger(ReasoningResource.class);

    @Inject
    private ReasoningService reasoningService;

    @Inject
    private OpenAMSecurityHelper openAMSecurityHelper;
    @Inject
    private TestbedClientService testbedClientService;
    @Inject
    private ReasoningMapper reasoningMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * POST  /reasonings : Create a new reasoning.
     *
     * @param reasoning the reasoning to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reasoning, or with status 400 (Bad Request) if the reasoning has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reasonings")
    @Timed
    public ResponseEntity<?> createReasoning(@Valid @RequestBody CreateRuleWithExpertRequest reasoning, HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to save Reasoning : {}", reasoning);

        try {



            String token = openAMSecurityHelper.getToken(request);
            log.info("REST request with cookie token : {}", token);
            String userID = openAMSecurityHelper.getUserID(token);

            ValidateRuleRequest validateRequest = new ValidateRuleRequest();
            validateRequest.setRule(reasoning.getContent());
            validateRequest.setSensorId(reasoning.getSensor());

            ValidateRuleResponse response = reasoningService.validateRule(validateRequest, token);

            if (response.getResult()) {
                Reasoning result = new Reasoning();
                result.setUserId(userID);
                result.setSensor(reasoning.getSensor());
                result.setRuleType(1); // expert create rule
                result.setContent(reasoning.getContent());
                result.setCreated(new Date());
                result.setLatitude(reasoning.getLatitude());
                result.setLongitude(reasoning.getLongitude());
                result.setName(reasoning.getName());
                result.setQuantityKind(reasoning.getQuantityKind());
                result.setUnitOfMeasurement(reasoning.getUnitOfMeasurement());

                //reasoning.setRuleType(1);
                result = reasoningService.save(result);

                ReasoningDTO dto = reasoningMapper.reasoningToReasoningDTO(result);

                return ResponseEntity.created(new URI("/api/reasonings/" + dto.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("reasoning", dto.getId().toString()))
                    .body(dto);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reasoning", "message", "Error rule validation")).body(response);
            }
        } catch (Exception ex) {
            log.info("Error when createReasoning with message : {} ", ex.toString());
            log.error("Error when createReasoning with message : {} ", ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(HeaderUtil.createFailureAlert("excution", "message", "Error when re execute the rule")).body(null);
        }
    }

    @PostMapping("/reasonings/nonExpert")
    @Timed
    public ResponseEntity<?> createReasoningWithnonExpert(@Valid @RequestBody RuleCreationNonExpertRequest reasoning, HttpServletRequest request) throws URISyntaxException {
        try {
            log.info("REST request to save Reasoning : {}", reasoning);

            String token = openAMSecurityHelper.getToken(request);
            log.info("REST request with cookie token : {}", token);
            String userID = openAMSecurityHelper.getUserID(token);

            ObjectMapper objectMap = new ObjectMapper();
            List<RuleOperator> rules = reasoning.getRules();

            String orignialNonExpertRules = objectMap.writeValueAsString(rules);

            String ruleContent = RuleUtils.translateRule(rules);


            ValidateRuleRequest validateRequest = new ValidateRuleRequest();
            validateRequest.setRule(ruleContent);
            validateRequest.setSensorId(reasoning.getSensor());

            ValidateRuleResponse response = reasoningService.validateRule(validateRequest, token);

            if (response.getResult()) {
                Reasoning result = new Reasoning();
                //result.setRuleType(2);
                result.setUserId(userID);
                result.content(ruleContent);
                result.setDescription(reasoning.getDescription());
                result.setName(reasoning.getName());
                result.setQuantityKind(reasoning.getQuantityKind());
                result.setUnitOfMeasurement(reasoning.getUnitOfMeasurement());
                result.setLatitude(reasoning.getLatitude());
                result.setLongitude(reasoning.getLongitude());
                result.setSensor(reasoning.getSensor());
                result.setCreated(new Date());
                result.setSensorEndp(reasoning.getSensorEndp());
                result.setSensorSampleData(reasoning.getSensorSampleData());
                result.setSensorMeta(reasoning.getSensorMeta());
                result.setRuleType(2); // non-expert rule
                result.setNonExpertOriginalRules(orignialNonExpertRules);

                result = reasoningService.save(result);

                ReasoningDTO dto = reasoningMapper.reasoningToReasoningDTO(result);

                return ResponseEntity.created(new URI("/api/reasonings/" + dto.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert("reasoning", dto.getId().toString()))
                    .body(dto);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reasoning", "idexists", "Error rule validation")).body(response);
            }
        } catch (Exception ex) {
            log.info("Error when createReasoning with message : {} ", ex.toString());
            log.error("Error when createReasoningWithnonExpert with error message: {} ", ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(HeaderUtil.createFailureAlert("reasoning", "error", "Error when create new  rule")).body(null);
        }

    }

    @PostMapping("/rule/validate")
    @Timed
    public ResponseEntity<ValidateRuleResponse> validateRule(@Valid @RequestBody ValidateRuleRequest validateRequest, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Reasoning : {}", validateRequest);

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);

        ValidateRuleResponse response = reasoningService.validateRule(validateRequest, token);

       // Reasoning result = reasoningService.save(reasoning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert("reasoning", response.getResult().toString()))
            .body(response);
    }


    /**
     * PUT  /reasonings : Updates an existing reasoning.
     *
     * @param reasoning the reasoning to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reasoning,
     * or with status 400 (Bad Request) if the reasoning is not valid,
     * or with status 500 (Internal Server Error) if the reasoning couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reasonings")
    @Timed
    public ResponseEntity<?> updateReasoning(@Valid @RequestBody UpdateRuleWithExpertRequest reasoning, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Reasoning : {}", reasoning);
        if (reasoning.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reasoning", "message", "Can not update rule without rule id!")).body(null);
        }

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);


        Reasoning result = reasoningService.findOne(reasoning.getId());
        if(result == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reasoning", "message", "Can not update rule invalid rule id!")).body(null);
        }


        log.info("Current userID: {}, rule userID: {}", userID, result.getUserId());
        if(!result.getUserId().equalsIgnoreCase(userID)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reasoning", "message", "You can not update this rule because you don not have permission !")).body(null);

        }
        ValidateRuleRequest validateRequest = new ValidateRuleRequest();
        validateRequest.setRule(reasoning.getContent());
        validateRequest.setSensorId(reasoning.getSensor());

        ValidateRuleResponse response = reasoningService.validateRule(validateRequest, token);

        if(response.getResult()) {

            result.setUserId(userID);
            result.setLongitude(reasoning.getLongitude());
            result.setLatitude(reasoning.getLatitude());
            result.setUnitOfMeasurement(reasoning.getUnitOfMeasurement());
            result.setQuantityKind(reasoning.getQuantityKind());
            result.setContent(reasoning.getContent());
            result.setName(reasoning.getName());
            result.setDescription(reasoning.getDescription());
            result.setSensor(reasoning.getSensor());
            result.setUpdated(new Date());


            result = reasoningService.save(result);
            ReasoningDTO dto = reasoningMapper.reasoningToReasoningDTO(result);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert("reasoning", dto.getId().toString()))
                .body(dto);
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reasoning", "idexists", "Error rule validation")).body(response);
        }
    }

    /**
     * GET  /reasonings : get all the reasonings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reasonings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/reasonings")
    @Timed
    public ResponseEntity<List<ReasoningDTO>> getAllReasonings(@ApiParam Pageable pageable)
        throws URISyntaxException {
        try {
            log.info("REST request to get a page of Reasonings");

            Page<Reasoning> page = reasoningService.findAll(pageable);

            Page<ReasoningDTO> result = page.map(reasoning -> reasoningMapper.reasoningToReasoningDTO(reasoning));

            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, "/api/reasonings");
            return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
        } catch (Exception ex) {

            log.info("Error on getAllReasonings with message: {} ", ex.toString());
            log.error("Error on getAllReasonings with message: {} ", ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(HeaderUtil.createFailureAlert("excution", "idexists", "Error when re execute the rule")).body(null);
        }
    }

    /**
     * GET  /reasonings/:id : get the "id" reasoning.
     *
     * @param id the id of the reasoning to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reasoning, or with status 404 (Not Found)
     */
    @GetMapping("/reasonings/{id}")
    @Timed
    public ResponseEntity<ReasoningDTO> getReasoning(@PathVariable Long id) {
        log.debug("REST request to get Reasoning : {}", id);
        Reasoning reasoning = reasoningService.findOne(id);
        ReasoningDTO dto = reasoningMapper.reasoningToReasoningDTO(reasoning);
        return Optional.ofNullable(dto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/reasonings/statistic")
    @Timed
    public ResponseEntity<ReasoningStatisticResponse> reasoningsStatistic() {

        ReasoningStatisticResponse reasoningStatisticResponse = new ReasoningStatisticResponse();
        reasoningStatisticResponse.setCreated(reasoningCreated());
        reasoningStatisticResponse.setUsed(reasoningUsed());

        return new ResponseEntity<>(reasoningStatisticResponse, null, HttpStatus.OK);
    }

    private HashMap<String, Long> initStatistic(){
        HashMap<String, Long> data = new LinkedHashMap<>();
        for(int i = 1; i<=12;i++) {
            //int staticData = i*10;
            data.put(Month.of(i).name(), 0L);
        }
        return data;
    }

    private ReasoningStatistic reasoningCreated() {
        ReasoningStatistic statistic = new ReasoningStatistic();
        HashMap<String, Long> data = initStatistic();


        List<RegisterRuleSummary> result = jdbcTemplate.query(
            "select count(id) as total, MONTH(created) as created from reasoning\n" +
                "where year(created) = year(now())\n" +
                "GROUP BY  MONTH(created)",
            new RowMapper<RegisterRuleSummary>() {
                public RegisterRuleSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
                    RegisterRuleSummary c = new RegisterRuleSummary();
                   // c.setTotal(rs.getLong(1));
                   // c.setCreated(rs.getInt(2));
                    data.put(Month.of(rs.getInt(2)).name(), rs.getLong(1));

                    return c;
                }
            });

        statistic.setDay(new ArrayList<>(data.keySet()));
        statistic.setData(new ArrayList<>(data.values()));
        return statistic;
    }

    private ReasoningStatistic reasoningUsed() {
        ReasoningStatistic statistic = new ReasoningStatistic();
        HashMap<String, Long> data = initStatistic();


        List<RegisterRuleSummary> result = jdbcTemplate.query(
            "select count(id) as total, MONTH(created) as created from register_rule\n" +
                "where year(created) = year(now())\n" +
                "GROUP BY  MONTH(created)",
            new RowMapper<RegisterRuleSummary>() {
                public RegisterRuleSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
                    RegisterRuleSummary c = new RegisterRuleSummary();
                   // c.setTotal(rs.getLong(1));
                    //c.setCreated(rs.getDate(2));
                    data.put(Month.of(rs.getInt(2)).name(), rs.getLong(1));

                    return c;
                }
            });



        statistic.setDay(new ArrayList<>(data.keySet()));
        statistic.setData(new ArrayList<>(data.values()));

        return statistic;
    }
    /**
     * DELETE  /reasonings/:id : delete the "id" reasoning.
     *
     * @param id the id of the reasoning to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    /*@DeleteMapping("/reasonings/{id}")
    @Timed
    public ResponseEntity<Void> deleteReasoning(@PathVariable Long id) {
        log.debug("REST request to delete Reasoning : {}", id);
        reasoningService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reasoning", id.toString())).build();
    }*/

}
