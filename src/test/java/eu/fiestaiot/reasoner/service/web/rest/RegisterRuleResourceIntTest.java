package eu.fiestaiot.reasoner.service.web.rest;

import eu.fiestaiot.reasoner.service.FiestaReasonerEngineApp;

import eu.fiestaiot.reasoner.service.domain.RegisterRule;
import eu.fiestaiot.reasoner.service.domain.Reasoning;
import eu.fiestaiot.reasoner.service.repository.RegisterRuleRepository;
import eu.fiestaiot.reasoner.service.service.RegisterRuleService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RegisterRuleResource REST controller.
 *
 * @see RegisterRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FiestaReasonerEngineApp.class)
public class RegisterRuleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_SENSOR = "AAAAAAAAAA";
    private static final String UPDATED_SENSOR = "BBBBBBBBBB";

    private static final String DEFAULT_SENSOR_META = "AAAAAAAAAA";
    private static final String UPDATED_SENSOR_META = "BBBBBBBBBB";

    private static final String DEFAULT_INFERRED_DATA = "AAAAAAAAAA";
    private static final String UPDATED_INFERRED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_DATA = "AAAAAAAAAA";
    private static final String UPDATED_FULL_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Inject
    private RegisterRuleRepository registerRuleRepository;

    @Inject
    private RegisterRuleService registerRuleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRegisterRuleMockMvc;

    private RegisterRule registerRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegisterRuleResource registerRuleResource = new RegisterRuleResource();
        ReflectionTestUtils.setField(registerRuleResource, "registerRuleService", registerRuleService);
        this.restRegisterRuleMockMvc = MockMvcBuilders.standaloneSetup(registerRuleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegisterRule createEntity(EntityManager em) {
        RegisterRule registerRule = new RegisterRule()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .ruleContent(DEFAULT_RULE_CONTENT)
                .data(DEFAULT_DATA)
                .sensor(DEFAULT_SENSOR)
                .sensorMeta(DEFAULT_SENSOR_META)
                .inferredData(DEFAULT_INFERRED_DATA)
                .fullData(DEFAULT_FULL_DATA)
                .userId(DEFAULT_USER_ID);
        // Add required entity
        Reasoning reasoning = ReasoningResourceIntTest.createEntity(em);
        em.persist(reasoning);
        em.flush();
        registerRule.setReasoning(reasoning);
        return registerRule;
    }

    @Before
    public void initTest() {
        registerRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegisterRule() throws Exception {
        int databaseSizeBeforeCreate = registerRuleRepository.findAll().size();

        // Create the RegisterRule

        restRegisterRuleMockMvc.perform(post("/api/register-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerRule)))
            .andExpect(status().isCreated());

        // Validate the RegisterRule in the database
        List<RegisterRule> registerRuleList = registerRuleRepository.findAll();
        assertThat(registerRuleList).hasSize(databaseSizeBeforeCreate + 1);
        RegisterRule testRegisterRule = registerRuleList.get(registerRuleList.size() - 1);
        assertThat(testRegisterRule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegisterRule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRegisterRule.getRuleContent()).isEqualTo(DEFAULT_RULE_CONTENT);
        assertThat(testRegisterRule.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testRegisterRule.getSensor()).isEqualTo(DEFAULT_SENSOR);
        assertThat(testRegisterRule.getSensorMeta()).isEqualTo(DEFAULT_SENSOR_META);
        assertThat(testRegisterRule.getInferredData()).isEqualTo(DEFAULT_INFERRED_DATA);
        assertThat(testRegisterRule.getFullData()).isEqualTo(DEFAULT_FULL_DATA);
        assertThat(testRegisterRule.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createRegisterRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registerRuleRepository.findAll().size();

        // Create the RegisterRule with an existing ID
        RegisterRule existingRegisterRule = new RegisterRule();
        existingRegisterRule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegisterRuleMockMvc.perform(post("/api/register-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRegisterRule)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RegisterRule> registerRuleList = registerRuleRepository.findAll();
        assertThat(registerRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerRuleRepository.findAll().size();
        // set the field null
        registerRule.setName(null);

        // Create the RegisterRule, which fails.

        restRegisterRuleMockMvc.perform(post("/api/register-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerRule)))
            .andExpect(status().isBadRequest());

        List<RegisterRule> registerRuleList = registerRuleRepository.findAll();
        assertThat(registerRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerRuleRepository.findAll().size();
        // set the field null
        registerRule.setRuleContent(null);

        // Create the RegisterRule, which fails.

        restRegisterRuleMockMvc.perform(post("/api/register-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerRule)))
            .andExpect(status().isBadRequest());

        List<RegisterRule> registerRuleList = registerRuleRepository.findAll();
        assertThat(registerRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerRuleRepository.findAll().size();
        // set the field null
        registerRule.setData(null);

        // Create the RegisterRule, which fails.

        restRegisterRuleMockMvc.perform(post("/api/register-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerRule)))
            .andExpect(status().isBadRequest());

        List<RegisterRule> registerRuleList = registerRuleRepository.findAll();
        assertThat(registerRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSensorIsRequired() throws Exception {
        int databaseSizeBeforeTest = registerRuleRepository.findAll().size();
        // set the field null
        registerRule.setSensor(null);

        // Create the RegisterRule, which fails.

        restRegisterRuleMockMvc.perform(post("/api/register-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerRule)))
            .andExpect(status().isBadRequest());

        List<RegisterRule> registerRuleList = registerRuleRepository.findAll();
        assertThat(registerRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegisterRules() throws Exception {
        // Initialize the database
        registerRuleRepository.saveAndFlush(registerRule);

        // Get all the registerRuleList
        restRegisterRuleMockMvc.perform(get("/api/register-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registerRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].ruleContent").value(hasItem(DEFAULT_RULE_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].sensor").value(hasItem(DEFAULT_SENSOR.toString())))
            .andExpect(jsonPath("$.[*].sensorMeta").value(hasItem(DEFAULT_SENSOR_META.toString())))
            .andExpect(jsonPath("$.[*].inferredData").value(hasItem(DEFAULT_INFERRED_DATA.toString())))
            .andExpect(jsonPath("$.[*].fullData").value(hasItem(DEFAULT_FULL_DATA.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())));
    }

    @Test
    @Transactional
    public void getRegisterRule() throws Exception {
        // Initialize the database
        registerRuleRepository.saveAndFlush(registerRule);

        // Get the registerRule
        restRegisterRuleMockMvc.perform(get("/api/register-rules/{id}", registerRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registerRule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.ruleContent").value(DEFAULT_RULE_CONTENT.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.sensor").value(DEFAULT_SENSOR.toString()))
            .andExpect(jsonPath("$.sensorMeta").value(DEFAULT_SENSOR_META.toString()))
            .andExpect(jsonPath("$.inferredData").value(DEFAULT_INFERRED_DATA.toString()))
            .andExpect(jsonPath("$.fullData").value(DEFAULT_FULL_DATA.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegisterRule() throws Exception {
        // Get the registerRule
        restRegisterRuleMockMvc.perform(get("/api/register-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegisterRule() throws Exception {
        // Initialize the database
        registerRuleService.save(registerRule);

        int databaseSizeBeforeUpdate = registerRuleRepository.findAll().size();

        // Update the registerRule
        RegisterRule updatedRegisterRule = registerRuleRepository.findOne(registerRule.getId());
        updatedRegisterRule
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .ruleContent(UPDATED_RULE_CONTENT)
                .data(UPDATED_DATA)
                .sensor(UPDATED_SENSOR)
                .sensorMeta(UPDATED_SENSOR_META)
                .inferredData(UPDATED_INFERRED_DATA)
                .fullData(UPDATED_FULL_DATA)
                .userId(UPDATED_USER_ID);

        restRegisterRuleMockMvc.perform(put("/api/register-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegisterRule)))
            .andExpect(status().isOk());

        // Validate the RegisterRule in the database
        List<RegisterRule> registerRuleList = registerRuleRepository.findAll();
        assertThat(registerRuleList).hasSize(databaseSizeBeforeUpdate);
        RegisterRule testRegisterRule = registerRuleList.get(registerRuleList.size() - 1);
        assertThat(testRegisterRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegisterRule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRegisterRule.getRuleContent()).isEqualTo(UPDATED_RULE_CONTENT);
        assertThat(testRegisterRule.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testRegisterRule.getSensor()).isEqualTo(UPDATED_SENSOR);
        assertThat(testRegisterRule.getSensorMeta()).isEqualTo(UPDATED_SENSOR_META);
        assertThat(testRegisterRule.getInferredData()).isEqualTo(UPDATED_INFERRED_DATA);
        assertThat(testRegisterRule.getFullData()).isEqualTo(UPDATED_FULL_DATA);
        assertThat(testRegisterRule.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingRegisterRule() throws Exception {
        int databaseSizeBeforeUpdate = registerRuleRepository.findAll().size();

        // Create the RegisterRule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegisterRuleMockMvc.perform(put("/api/register-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registerRule)))
            .andExpect(status().isCreated());

        // Validate the RegisterRule in the database
        List<RegisterRule> registerRuleList = registerRuleRepository.findAll();
        assertThat(registerRuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegisterRule() throws Exception {
        // Initialize the database
        registerRuleService.save(registerRule);

        int databaseSizeBeforeDelete = registerRuleRepository.findAll().size();

        // Get the registerRule
        restRegisterRuleMockMvc.perform(delete("/api/register-rules/{id}", registerRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RegisterRule> registerRuleList = registerRuleRepository.findAll();
        assertThat(registerRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
