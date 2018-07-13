package eu.fiestaiot.reasoner.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.reasoner.service.domain.Reasoning;
import eu.fiestaiot.reasoner.service.domain.RegisterRule;
import eu.fiestaiot.reasoner.service.service.OpenAMSecurityHelper;
import eu.fiestaiot.reasoner.service.service.ReasoningService;
import eu.fiestaiot.reasoner.service.service.RegisterRuleService;
import eu.fiestaiot.reasoner.service.service.TestbedClientService;
import eu.fiestaiot.reasoner.service.service.dto.Sensor;
import eu.fiestaiot.reasoner.service.service.mapper.RegisterRuleMapper;
import eu.fiestaiot.reasoner.service.web.rest.util.HeaderUtil;
import eu.fiestaiot.reasoner.service.web.rest.util.PaginationUtil;

import eu.fiestaiot.reasoner.service.web.rest.vm.dto.RegisterRuleDTO;
import eu.fiestaiot.reasoner.service.web.rest.vm.request.RegisterRuleRequest;
import eu.fiestaiot.reasoner.service.web.rest.vm.request.UpdateRegisterRuleRequest;
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
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RegisterRule.
 */
@RestController
@RequestMapping("/api")
public class RegisterRuleResource {

    private final Logger log = LoggerFactory.getLogger(RegisterRuleResource.class);

    @Inject
    private RegisterRuleService registerRuleService;

    @Inject
    private OpenAMSecurityHelper openAMSecurityHelper;

    @Inject
    private ReasoningService reasoingService;

    @Inject
    private TestbedClientService testbedClientService;

    @Inject

    private RegisterRuleMapper registerRuleMapper;

    /**
     * POST  /register-rules : Create a new registerRule.
     *
     * @param registerRule the registerRule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new registerRule, or with status 400 (Bad Request) if the registerRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/register-rules")
    @Timed
    public ResponseEntity<?> createRegisterRule(@Valid @RequestBody RegisterRuleRequest registerRule, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save RegisterRule : {}", registerRule);
        /*if (registerRule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("registerRule", "idexists", "A new registerRule cannot already have an ID")).body(null);
        }*/

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);


        RegisterRule result;
        Reasoning rule  = reasoingService.findOne(registerRule.getRuleId());

        RegisterRule ruleRegister = new RegisterRule();


        ruleRegister.setUserId(userID);
        ruleRegister.setName(registerRule.getName());
        ruleRegister.setDescription(registerRule.getDescription());
        ruleRegister.setSensor(registerRule.getSensor());
        ruleRegister.setLatitude(registerRule.getLatitude());
        ruleRegister.setLongitude(registerRule.getLongitude());
        ruleRegister.setQuantityKind(registerRule.getQuantityKind());
        ruleRegister.setUnitOfMeasurement(registerRule.getUnitOfMeasurement());
        ruleRegister.setRuleContent(rule.getContent());
        ruleRegister.setReasoning(rule);

        Sensor sensor = testbedClientService.getSensorInformationByID( registerRule.getSensor(), token);

        if(sensor == null || sensor.getEndp() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("registerRule", "message", "Can not get sensor endpoint by given sensor ID, Please try again sensor!")).body(null);
        }

        ruleRegister.setSensorEndp(sensor.getEndp());
        ruleRegister.setData(sensor.getSensorData());


        RegisterRule excuteRule = registerRuleService.executeReasoning(ruleRegister);
        if(excuteRule != null) {
            excuteRule.setCreated(new Date());
            result = registerRuleService.save(excuteRule);
        } else {
            result = registerRuleService.save(ruleRegister);
        }

        RegisterRuleDTO dto = registerRuleMapper.registerRuleToRegisterRuleDTO(result);
        return ResponseEntity.created(new URI("/api/register-rules/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("registerRule", dto.getId().toString()))
            .body(dto);
    }





    /**
     * PUT  /register-rules : Updates an existing registerRule.
     *
     * @param registerRule the registerRule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated registerRule,
     * or with status 400 (Bad Request) if the registerRule is not valid,
     * or with status 500 (Internal Server Error) if the registerRule couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/register-rules")
    @Timed
    public ResponseEntity<RegisterRuleDTO> updateRegisterRule(@Valid @RequestBody UpdateRegisterRuleRequest registerRule, HttpServletRequest request) throws URISyntaxException {

        log.debug("REST request to update RegisterRule : {}", registerRule);
        if (registerRule.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("registerRule", "message", "A registerRule cannot already have an empty ID!")).body(null);
        }
        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);


        RegisterRule result = registerRuleService.findOne(registerRule.getId());
        if(result == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("registerRule", "message", "Not found any register rule given by this ID!")).body(null);
        }

        if(!result.getUserId().equalsIgnoreCase(userID)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reasoning", "message", "You can not update this rule registration because you don not have permission !")).body(null);
        }

        Reasoning rule = reasoingService.findOne(registerRule.getRuleId());
        if(rule == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("registerRule", "message", "Not found any rule given by this ID!")).body(null);
        }

        result.setName(registerRule.getName());
        result.setDescription(registerRule.getDescription());
        result.setSensor(registerRule.getSensor());
        result.setQuantityKind(registerRule.getQuantityKind());
        result.setUnitOfMeasurement(registerRule.getUnitOfMeasurement());
        result.setLongitude(registerRule.getLongitude());
        result.setLatitude(registerRule.getLatitude());
        result.setReasoning(rule);

        Sensor sensor = testbedClientService.getSensorInformationByID( registerRule.getSensor(), token);

        if(sensor == null || sensor.getEndp() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("registerRule", "message", "Can not get sensor endpoint by given sensor ID, Please try again sensor!")).body(null);
        }
        result.setSensorEndp(sensor.getEndp());
        result.setData(sensor.getSensorData());

        RegisterRule excuteRule = registerRuleService.executeReasoning(result);
        excuteRule.setUserId(userID);
        if(excuteRule != null) {
            excuteRule.setUpdated(new Date());
            result = registerRuleService.save(excuteRule);
        } else {
            result = registerRuleService.save(result);
        }

        RegisterRuleDTO dto = registerRuleMapper.registerRuleToRegisterRuleDTO(result);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("registerRule", dto.getId().toString()))
            .body(dto);
    }

    /**
     * GET  /register-rules : get all the registerRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of registerRules in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/register-rules")
    @Timed
    public ResponseEntity<List<RegisterRuleDTO>> getAllRegisterRules(@ApiParam Pageable pageable,HttpServletRequest request)
        throws URISyntaxException {

        log.debug("REST request to get a page of RegisterRules");
        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);

        Page<RegisterRule> result = registerRuleService.findAllByUserId(userID, pageable);

        Page<RegisterRuleDTO> registerRuleDTOS = result.map(registerRule -> registerRuleMapper.registerRuleToRegisterRuleDTO(registerRule));

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(registerRuleDTOS, "/api/register-rules");
        return new ResponseEntity<>(registerRuleDTOS.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /register-rules/:id : get the "id" registerRule.
     *
     * @param id the id of the registerRule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the registerRule, or with status 404 (Not Found)
     */
    @GetMapping("/register-rules/{id}")
    @Timed
    public ResponseEntity<RegisterRuleDTO> getRegisterRule(@PathVariable Long id) {
        log.debug("REST request to get RegisterRule : {}", id);
        RegisterRule registerRule = registerRuleService.findOne(id);

        RegisterRuleDTO dto = registerRuleMapper.registerRuleToRegisterRuleDTO(registerRule);

        registerRule.setHashedSensor(registerRule.getSensor());
        return Optional.ofNullable(dto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /register-rules/:id : delete the "id" registerRule.
     *
     * @param id the id of the registerRule to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    /*@DeleteMapping("/register-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegisterRule(@PathVariable Long id) {
        log.debug("REST request to delete RegisterRule : {}", id);
        registerRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("registerRule", id.toString())).build();
    } */

}
