package eu.fiestaiot.reasoner.service.web.rest;

import eu.fiestaiot.reasoner.service.FiestaReasonerEngineApp;

import eu.fiestaiot.reasoner.service.domain.Reasoning;
import eu.fiestaiot.reasoner.service.repository.ReasoningRepository;
import eu.fiestaiot.reasoner.service.service.ReasoningService;

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

import java.util.Date;
import java.util.List;

import static eu.fiestaiot.reasoner.service.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReasoningResource REST controller.
 *
 * @see ReasoningResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FiestaReasonerEngineApp.class)
public class ReasoningResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Date DEFAULT_CREATED = new Date();//Date.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final Date UPDATED_CREATED = new Date();//Date.now(ZoneId.systemDefault()).withNano(0);

    private static final Date DEFAULT_UPDATED = new Date();//Date.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final Date UPDATED_UPDATED = new Date();
    //Date.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_SENSOR = "AAAAAAAAAA";
    private static final String UPDATED_SENSOR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private ReasoningRepository reasoningRepository;

    @Inject
    private ReasoningService reasoningService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restReasoningMockMvc;

    private Reasoning reasoning;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReasoningResource reasoningResource = new ReasoningResource();
        ReflectionTestUtils.setField(reasoningResource, "reasoningService", reasoningService);
        this.restReasoningMockMvc = MockMvcBuilders.standaloneSetup(reasoningResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reasoning createEntity(EntityManager em) {
        Reasoning reasoning = new Reasoning()
                .name(DEFAULT_NAME)
                .userId(DEFAULT_USER_ID)
               // .created(DEFAULT_CREATED)
                //.updated(DEFAULT_UPDATED)
                .content(DEFAULT_CONTENT)
                .sensor(DEFAULT_SENSOR)
                .description(DEFAULT_DESCRIPTION);
        return reasoning;
    }

    @Before
    public void initTest() {
        reasoning = createEntity(em);
    }

    @Test
    @Transactional
    public void createReasoning() throws Exception {
        int databaseSizeBeforeCreate = reasoningRepository.findAll().size();

        // Create the Reasoning

        restReasoningMockMvc.perform(post("/api/reasonings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasoning)))
            .andExpect(status().isCreated());

        // Validate the Reasoning in the database
        List<Reasoning> reasoningList = reasoningRepository.findAll();
        assertThat(reasoningList).hasSize(databaseSizeBeforeCreate + 1);
        Reasoning testReasoning = reasoningList.get(reasoningList.size() - 1);
        assertThat(testReasoning.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReasoning.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testReasoning.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testReasoning.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testReasoning.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testReasoning.getSensor()).isEqualTo(DEFAULT_SENSOR);
        assertThat(testReasoning.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReasoningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reasoningRepository.findAll().size();

        // Create the Reasoning with an existing ID
        Reasoning existingReasoning = new Reasoning();
        existingReasoning.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReasoningMockMvc.perform(post("/api/reasonings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingReasoning)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Reasoning> reasoningList = reasoningRepository.findAll();
        assertThat(reasoningList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reasoningRepository.findAll().size();
        // set the field null
        reasoning.setName(null);

        // Create the Reasoning, which fails.

        restReasoningMockMvc.perform(post("/api/reasonings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasoning)))
            .andExpect(status().isBadRequest());

        List<Reasoning> reasoningList = reasoningRepository.findAll();
        assertThat(reasoningList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reasoningRepository.findAll().size();
        // set the field null
        reasoning.setUserId(null);

        // Create the Reasoning, which fails.

        restReasoningMockMvc.perform(post("/api/reasonings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasoning)))
            .andExpect(status().isBadRequest());

        List<Reasoning> reasoningList = reasoningRepository.findAll();
        assertThat(reasoningList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = reasoningRepository.findAll().size();
        // set the field null
        reasoning.setContent(null);

        // Create the Reasoning, which fails.

        restReasoningMockMvc.perform(post("/api/reasonings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasoning)))
            .andExpect(status().isBadRequest());

        List<Reasoning> reasoningList = reasoningRepository.findAll();
        assertThat(reasoningList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSensorIsRequired() throws Exception {
        int databaseSizeBeforeTest = reasoningRepository.findAll().size();
        // set the field null
        reasoning.setSensor(null);

        // Create the Reasoning, which fails.

        restReasoningMockMvc.perform(post("/api/reasonings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasoning)))
            .andExpect(status().isBadRequest());

        List<Reasoning> reasoningList = reasoningRepository.findAll();
        assertThat(reasoningList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReasonings() throws Exception {
        // Initialize the database
        reasoningRepository.saveAndFlush(reasoning);

        // Get all the reasoningList
        restReasoningMockMvc.perform(get("/api/reasonings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reasoning.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            //.andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            //.andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].sensor").value(hasItem(DEFAULT_SENSOR.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getReasoning() throws Exception {
        // Initialize the database
        reasoningRepository.saveAndFlush(reasoning);

        // Get the reasoning
        restReasoningMockMvc.perform(get("/api/reasonings/{id}", reasoning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reasoning.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
           // .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
           // .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.sensor").value(DEFAULT_SENSOR.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReasoning() throws Exception {
        // Get the reasoning
        restReasoningMockMvc.perform(get("/api/reasonings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReasoning() throws Exception {
        // Initialize the database
        reasoningService.save(reasoning);

        int databaseSizeBeforeUpdate = reasoningRepository.findAll().size();

        // Update the reasoning
        Reasoning updatedReasoning = reasoningRepository.findOne(reasoning.getId());
        updatedReasoning
                .name(UPDATED_NAME)
                .userId(UPDATED_USER_ID)
                //.created(UPDATED_CREATED)
                //.updated(UPDATED_UPDATED)
                .content(UPDATED_CONTENT)
                .sensor(UPDATED_SENSOR)
                .description(UPDATED_DESCRIPTION);

        restReasoningMockMvc.perform(put("/api/reasonings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReasoning)))
            .andExpect(status().isOk());

        // Validate the Reasoning in the database
        List<Reasoning> reasoningList = reasoningRepository.findAll();
        assertThat(reasoningList).hasSize(databaseSizeBeforeUpdate);
        Reasoning testReasoning = reasoningList.get(reasoningList.size() - 1);
        assertThat(testReasoning.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReasoning.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testReasoning.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testReasoning.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testReasoning.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testReasoning.getSensor()).isEqualTo(UPDATED_SENSOR);
        assertThat(testReasoning.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReasoning() throws Exception {
        int databaseSizeBeforeUpdate = reasoningRepository.findAll().size();

        // Create the Reasoning

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReasoningMockMvc.perform(put("/api/reasonings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasoning)))
            .andExpect(status().isCreated());

        // Validate the Reasoning in the database
        List<Reasoning> reasoningList = reasoningRepository.findAll();
        assertThat(reasoningList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReasoning() throws Exception {
        // Initialize the database
        reasoningService.save(reasoning);

        int databaseSizeBeforeDelete = reasoningRepository.findAll().size();

        // Get the reasoning
        restReasoningMockMvc.perform(delete("/api/reasonings/{id}", reasoning.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reasoning> reasoningList = reasoningRepository.findAll();
        assertThat(reasoningList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
