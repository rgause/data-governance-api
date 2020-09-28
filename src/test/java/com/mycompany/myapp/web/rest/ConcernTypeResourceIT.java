package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DataGovernanceApiApp;
import com.mycompany.myapp.domain.ConcernType;
import com.mycompany.myapp.repository.ConcernTypeRepository;
import com.mycompany.myapp.service.ConcernTypeService;

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
 * Integration tests for the {@link ConcernTypeResource} REST controller.
 */
@SpringBootTest(classes = DataGovernanceApiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConcernTypeResourceIT {

    private static final String DEFAULT_CONCERN_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONCERN_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private ConcernTypeRepository concernTypeRepository;

    @Autowired
    private ConcernTypeService concernTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcernTypeMockMvc;

    private ConcernType concernType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcernType createEntity(EntityManager em) {
        ConcernType concernType = new ConcernType()
            .concernTypeName(DEFAULT_CONCERN_TYPE_NAME);
        return concernType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcernType createUpdatedEntity(EntityManager em) {
        ConcernType concernType = new ConcernType()
            .concernTypeName(UPDATED_CONCERN_TYPE_NAME);
        return concernType;
    }

    @BeforeEach
    public void initTest() {
        concernType = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcernType() throws Exception {
        int databaseSizeBeforeCreate = concernTypeRepository.findAll().size();
        // Create the ConcernType
        restConcernTypeMockMvc.perform(post("/api/concern-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concernType)))
            .andExpect(status().isCreated());

        // Validate the ConcernType in the database
        List<ConcernType> concernTypeList = concernTypeRepository.findAll();
        assertThat(concernTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ConcernType testConcernType = concernTypeList.get(concernTypeList.size() - 1);
        assertThat(testConcernType.getConcernTypeName()).isEqualTo(DEFAULT_CONCERN_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createConcernTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = concernTypeRepository.findAll().size();

        // Create the ConcernType with an existing ID
        concernType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcernTypeMockMvc.perform(post("/api/concern-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concernType)))
            .andExpect(status().isBadRequest());

        // Validate the ConcernType in the database
        List<ConcernType> concernTypeList = concernTypeRepository.findAll();
        assertThat(concernTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConcernTypes() throws Exception {
        // Initialize the database
        concernTypeRepository.saveAndFlush(concernType);

        // Get all the concernTypeList
        restConcernTypeMockMvc.perform(get("/api/concern-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concernType.getId().intValue())))
            .andExpect(jsonPath("$.[*].concernTypeName").value(hasItem(DEFAULT_CONCERN_TYPE_NAME)));
    }
    
    @Test
    @Transactional
    public void getConcernType() throws Exception {
        // Initialize the database
        concernTypeRepository.saveAndFlush(concernType);

        // Get the concernType
        restConcernTypeMockMvc.perform(get("/api/concern-types/{id}", concernType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concernType.getId().intValue()))
            .andExpect(jsonPath("$.concernTypeName").value(DEFAULT_CONCERN_TYPE_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingConcernType() throws Exception {
        // Get the concernType
        restConcernTypeMockMvc.perform(get("/api/concern-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcernType() throws Exception {
        // Initialize the database
        concernTypeService.save(concernType);

        int databaseSizeBeforeUpdate = concernTypeRepository.findAll().size();

        // Update the concernType
        ConcernType updatedConcernType = concernTypeRepository.findById(concernType.getId()).get();
        // Disconnect from session so that the updates on updatedConcernType are not directly saved in db
        em.detach(updatedConcernType);
        updatedConcernType
            .concernTypeName(UPDATED_CONCERN_TYPE_NAME);

        restConcernTypeMockMvc.perform(put("/api/concern-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConcernType)))
            .andExpect(status().isOk());

        // Validate the ConcernType in the database
        List<ConcernType> concernTypeList = concernTypeRepository.findAll();
        assertThat(concernTypeList).hasSize(databaseSizeBeforeUpdate);
        ConcernType testConcernType = concernTypeList.get(concernTypeList.size() - 1);
        assertThat(testConcernType.getConcernTypeName()).isEqualTo(UPDATED_CONCERN_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingConcernType() throws Exception {
        int databaseSizeBeforeUpdate = concernTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcernTypeMockMvc.perform(put("/api/concern-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concernType)))
            .andExpect(status().isBadRequest());

        // Validate the ConcernType in the database
        List<ConcernType> concernTypeList = concernTypeRepository.findAll();
        assertThat(concernTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConcernType() throws Exception {
        // Initialize the database
        concernTypeService.save(concernType);

        int databaseSizeBeforeDelete = concernTypeRepository.findAll().size();

        // Delete the concernType
        restConcernTypeMockMvc.perform(delete("/api/concern-types/{id}", concernType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConcernType> concernTypeList = concernTypeRepository.findAll();
        assertThat(concernTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
