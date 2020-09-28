package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DataGovernanceApiApp;
import com.mycompany.myapp.domain.Concern;
import com.mycompany.myapp.repository.ConcernRepository;
import com.mycompany.myapp.service.ConcernService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConcernResource} REST controller.
 */
@SpringBootTest(classes = DataGovernanceApiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConcernResourceIT {

    private static final String DEFAULT_CONCERN_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CONCERN_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ConcernRepository concernRepository;

    @Autowired
    private ConcernService concernService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcernMockMvc;

    private Concern concern;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concern createEntity(EntityManager em) {
        Concern concern = new Concern()
            .concernDescription(DEFAULT_CONCERN_DESCRIPTION);
        return concern;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concern createUpdatedEntity(EntityManager em) {
        Concern concern = new Concern()
            .concernDescription(UPDATED_CONCERN_DESCRIPTION);
        return concern;
    }

    @BeforeEach
    public void initTest() {
        concern = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcern() throws Exception {
        int databaseSizeBeforeCreate = concernRepository.findAll().size();
        // Create the Concern
        restConcernMockMvc.perform(post("/api/concerns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isCreated());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeCreate + 1);
        Concern testConcern = concernList.get(concernList.size() - 1);
        assertThat(testConcern.getConcernDescription()).isEqualTo(DEFAULT_CONCERN_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createConcernWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = concernRepository.findAll().size();

        // Create the Concern with an existing ID
        concern.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcernMockMvc.perform(post("/api/concerns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isBadRequest());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConcerns() throws Exception {
        // Initialize the database
        concernRepository.saveAndFlush(concern);

        // Get all the concernList
        restConcernMockMvc.perform(get("/api/concerns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concern.getId().intValue())))
            .andExpect(jsonPath("$.[*].concernDescription").value(hasItem(DEFAULT_CONCERN_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getConcern() throws Exception {
        // Initialize the database
        concernRepository.saveAndFlush(concern);

        // Get the concern
        restConcernMockMvc.perform(get("/api/concerns/{id}", concern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concern.getId().intValue()))
            .andExpect(jsonPath("$.concernDescription").value(DEFAULT_CONCERN_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingConcern() throws Exception {
        // Get the concern
        restConcernMockMvc.perform(get("/api/concerns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcern() throws Exception {
        // Initialize the database
        concernService.save(concern);

        int databaseSizeBeforeUpdate = concernRepository.findAll().size();

        // Update the concern
        Concern updatedConcern = concernRepository.findById(concern.getId()).get();
        // Disconnect from session so that the updates on updatedConcern are not directly saved in db
        em.detach(updatedConcern);
        updatedConcern
            .concernDescription(UPDATED_CONCERN_DESCRIPTION);

        restConcernMockMvc.perform(put("/api/concerns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConcern)))
            .andExpect(status().isOk());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
        Concern testConcern = concernList.get(concernList.size() - 1);
        assertThat(testConcern.getConcernDescription()).isEqualTo(UPDATED_CONCERN_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingConcern() throws Exception {
        int databaseSizeBeforeUpdate = concernRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcernMockMvc.perform(put("/api/concerns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concern)))
            .andExpect(status().isBadRequest());

        // Validate the Concern in the database
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConcern() throws Exception {
        // Initialize the database
        concernService.save(concern);

        int databaseSizeBeforeDelete = concernRepository.findAll().size();

        // Delete the concern
        restConcernMockMvc.perform(delete("/api/concerns/{id}", concern.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concern> concernList = concernRepository.findAll();
        assertThat(concernList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
