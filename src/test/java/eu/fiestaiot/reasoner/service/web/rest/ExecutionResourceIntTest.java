package eu.fiestaiot.reasoner.service.web.rest;

import eu.fiestaiot.reasoner.service.FiestaReasonerEngineApp;

import eu.fiestaiot.reasoner.service.domain.Execution;
import eu.fiestaiot.reasoner.service.domain.RegisterRule;
import eu.fiestaiot.reasoner.service.repository.ExecutionRepository;
import eu.fiestaiot.reasoner.service.service.ExecutionService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static eu.fiestaiot.reasoner.service.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExcutionResource REST controller.
 *
 * @see ExecutionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FiestaReasonerEngineApp.class)
public class ExecutionResourceIntTest {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_STARTED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STARTED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ENDED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENDED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_RULE_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_DATA = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_INFFERED_DATA = "AAAAAAAAAA";
    private static final String UPDATED_INFFERED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_DATA = "AAAAAAAAAA";
    private static final String UPDATED_FULL_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SENSOR = "AAAAAAAAAA";
    private static final String UPDATED_SENSOR = "BBBBBBBBBB";

    @Inject
    private ExecutionRepository excutionRepository;

    @Inject
    private ExecutionService excutionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExcutionMockMvc;

    private Execution execution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExecutionResource excutionResource = new ExecutionResource();
        ReflectionTestUtils.setField(excutionResource, "excutionService", excutionService);
        this.restExcutionMockMvc = MockMvcBuilders.standaloneSetup(excutionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Execution createEntity(EntityManager em) {
        Execution execution = new Execution()
                .status(DEFAULT_STATUS)
                //.created(DEFAULT_CREATED)
                //.updated(DEFAULT_UPDATED)
               // .started(DEFAULT_STARTED)
               // .ended(DEFAULT_ENDED)
                .ruleContent(DEFAULT_RULE_CONTENT)
                .originalData(DEFAULT_ORIGINAL_DATA)
                .infferedData(DEFAULT_INFFERED_DATA)
                .fullData(DEFAULT_FULL_DATA)
                .userId(DEFAULT_USER_ID)
                .sensor(DEFAULT_SENSOR);
        // Add required entity
        RegisterRule registerRule = RegisterRuleResourceIntTest.createEntity(em);
        em.persist(registerRule);
        em.flush();
        execution.setRegisterRule(registerRule);
        return execution;
    }

    @Before
    public void initTest() {
        execution = createEntity(em);
    }

    @Test
    @Transactional
    public void createExcution() throws Exception {
        int databaseSizeBeforeCreate = excutionRepository.findAll().size();

        // Create the Execution

        restExcutionMockMvc.perform(post("/api/excutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(execution)))
            .andExpect(status().isCreated());

        // Validate the Execution in the database
        List<Execution> executionList = excutionRepository.findAll();
        assertThat(executionList).hasSize(databaseSizeBeforeCreate + 1);
        Execution testExecution = executionList.get(executionList.size() - 1);
        assertThat(testExecution.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExecution.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testExecution.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testExecution.getStarted()).isEqualTo(DEFAULT_STARTED);
        assertThat(testExecution.getEnded()).isEqualTo(DEFAULT_ENDED);
        assertThat(testExecution.getRuleContent()).isEqualTo(DEFAULT_RULE_CONTENT);
        assertThat(testExecution.getOriginalData()).isEqualTo(DEFAULT_ORIGINAL_DATA);
        assertThat(testExecution.getInfferedData()).isEqualTo(DEFAULT_INFFERED_DATA);
        assertThat(testExecution.getFullData()).isEqualTo(DEFAULT_FULL_DATA);
        assertThat(testExecution.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testExecution.getSensor()).isEqualTo(DEFAULT_SENSOR);
    }

    @Test
    @Transactional
    public void createExcutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = excutionRepository.findAll().size();

        // Create the Execution with an existing ID
        Execution existingExecution = new Execution();
        existingExecution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExcutionMockMvc.perform(post("/api/excutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingExecution)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Execution> executionList = excutionRepository.findAll();
        assertThat(executionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExcutions() throws Exception {
        // Initialize the database
        excutionRepository.saveAndFlush(execution);

        // Get all the excutionList
        restExcutionMockMvc.perform(get("/api/excutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(execution.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))))
            .andExpect(jsonPath("$.[*].started").value(hasItem(sameInstant(DEFAULT_STARTED))))
            .andExpect(jsonPath("$.[*].ended").value(hasItem(sameInstant(DEFAULT_ENDED))))
            .andExpect(jsonPath("$.[*].ruleContent").value(hasItem(DEFAULT_RULE_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].originalData").value(hasItem(DEFAULT_ORIGINAL_DATA.toString())))
            .andExpect(jsonPath("$.[*].infferedData").value(hasItem(DEFAULT_INFFERED_DATA.toString())))
            .andExpect(jsonPath("$.[*].fullData").value(hasItem(DEFAULT_FULL_DATA.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].sensor").value(hasItem(DEFAULT_SENSOR.toString())));
    }

    @Test
    @Transactional
    public void getExcution() throws Exception {
        // Initialize the database
        excutionRepository.saveAndFlush(execution);

        // Get the execution
        restExcutionMockMvc.perform(get("/api/excutions/{id}", execution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(execution.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)))
            .andExpect(jsonPath("$.started").value(sameInstant(DEFAULT_STARTED)))
            .andExpect(jsonPath("$.ended").value(sameInstant(DEFAULT_ENDED)))
            .andExpect(jsonPath("$.ruleContent").value(DEFAULT_RULE_CONTENT.toString()))
            .andExpect(jsonPath("$.originalData").value(DEFAULT_ORIGINAL_DATA.toString()))
            .andExpect(jsonPath("$.infferedData").value(DEFAULT_INFFERED_DATA.toString()))
            .andExpect(jsonPath("$.fullData").value(DEFAULT_FULL_DATA.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.sensor").value(DEFAULT_SENSOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExcution() throws Exception {
        // Get the execution
        restExcutionMockMvc.perform(get("/api/excutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExcution() throws Exception {
        // Initialize the database
        excutionService.save(execution);

        int databaseSizeBeforeUpdate = excutionRepository.findAll().size();

        // Update the execution
        Execution updatedExecution = excutionRepository.findOne(execution.getId());
        updatedExecution
                .status(UPDATED_STATUS)
                //.created(UPDATED_CREATED)
                //.updated(UPDATED_UPDATED)
                //.started(UPDATED_STARTED)
                //.ended(UPDATED_ENDED)
                .ruleContent(UPDATED_RULE_CONTENT)
                .originalData(UPDATED_ORIGINAL_DATA)
                .infferedData(UPDATED_INFFERED_DATA)
                .fullData(UPDATED_FULL_DATA)
                .userId(UPDATED_USER_ID)
                .sensor(UPDATED_SENSOR);

        restExcutionMockMvc.perform(put("/api/excutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExecution)))
            .andExpect(status().isOk());

        // Validate the Execution in the database
        List<Execution> executionList = excutionRepository.findAll();
        assertThat(executionList).hasSize(databaseSizeBeforeUpdate);
        Execution testExecution = executionList.get(executionList.size() - 1);
        assertThat(testExecution.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExecution.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testExecution.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testExecution.getStarted()).isEqualTo(UPDATED_STARTED);
        assertThat(testExecution.getEnded()).isEqualTo(UPDATED_ENDED);
        assertThat(testExecution.getRuleContent()).isEqualTo(UPDATED_RULE_CONTENT);
        assertThat(testExecution.getOriginalData()).isEqualTo(UPDATED_ORIGINAL_DATA);
        assertThat(testExecution.getInfferedData()).isEqualTo(UPDATED_INFFERED_DATA);
        assertThat(testExecution.getFullData()).isEqualTo(UPDATED_FULL_DATA);
        assertThat(testExecution.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testExecution.getSensor()).isEqualTo(UPDATED_SENSOR);
    }

    @Test
    @Transactional
    public void updateNonExistingExcution() throws Exception {
        int databaseSizeBeforeUpdate = excutionRepository.findAll().size();

        // Create the Execution

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExcutionMockMvc.perform(put("/api/excutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(execution)))
            .andExpect(status().isCreated());

        // Validate the Execution in the database
        List<Execution> executionList = excutionRepository.findAll();
        assertThat(executionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExcution() throws Exception {
        // Initialize the database
        excutionService.save(execution);

        int databaseSizeBeforeDelete = excutionRepository.findAll().size();

        // Get the execution
        restExcutionMockMvc.perform(delete("/api/excutions/{id}", execution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Execution> executionList = excutionRepository.findAll();
        assertThat(executionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
