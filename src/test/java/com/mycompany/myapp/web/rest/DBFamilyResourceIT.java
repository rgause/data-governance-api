package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DataGovernanceApiApp;
import com.mycompany.myapp.domain.DBFamily;
import com.mycompany.myapp.repository.DBFamilyRepository;
import com.mycompany.myapp.service.DBFamilyService;

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
 * Integration tests for the {@link DBFamilyResource} REST controller.
 */
@SpringBootTest(classes = DataGovernanceApiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DBFamilyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DBFamilyRepository dBFamilyRepository;

    @Autowired
    private DBFamilyService dBFamilyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDBFamilyMockMvc;

    private DBFamily dBFamily;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBFamily createEntity(EntityManager em) {
        DBFamily dBFamily = new DBFamily()
            .name(DEFAULT_NAME);
        return dBFamily;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBFamily createUpdatedEntity(EntityManager em) {
        DBFamily dBFamily = new DBFamily()
            .name(UPDATED_NAME);
        return dBFamily;
    }

    @BeforeEach
    public void initTest() {
        dBFamily = createEntity(em);
    }

    @Test
    @Transactional
    public void createDBFamily() throws Exception {
        int databaseSizeBeforeCreate = dBFamilyRepository.findAll().size();
        // Create the DBFamily
        restDBFamilyMockMvc.perform(post("/api/db-families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBFamily)))
            .andExpect(status().isCreated());

        // Validate the DBFamily in the database
        List<DBFamily> dBFamilyList = dBFamilyRepository.findAll();
        assertThat(dBFamilyList).hasSize(databaseSizeBeforeCreate + 1);
        DBFamily testDBFamily = dBFamilyList.get(dBFamilyList.size() - 1);
        assertThat(testDBFamily.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDBFamilyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dBFamilyRepository.findAll().size();

        // Create the DBFamily with an existing ID
        dBFamily.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDBFamilyMockMvc.perform(post("/api/db-families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBFamily)))
            .andExpect(status().isBadRequest());

        // Validate the DBFamily in the database
        List<DBFamily> dBFamilyList = dBFamilyRepository.findAll();
        assertThat(dBFamilyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDBFamilies() throws Exception {
        // Initialize the database
        dBFamilyRepository.saveAndFlush(dBFamily);

        // Get all the dBFamilyList
        restDBFamilyMockMvc.perform(get("/api/db-families?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dBFamily.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDBFamily() throws Exception {
        // Initialize the database
        dBFamilyRepository.saveAndFlush(dBFamily);

        // Get the dBFamily
        restDBFamilyMockMvc.perform(get("/api/db-families/{id}", dBFamily.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dBFamily.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDBFamily() throws Exception {
        // Get the dBFamily
        restDBFamilyMockMvc.perform(get("/api/db-families/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDBFamily() throws Exception {
        // Initialize the database
        dBFamilyService.save(dBFamily);

        int databaseSizeBeforeUpdate = dBFamilyRepository.findAll().size();

        // Update the dBFamily
        DBFamily updatedDBFamily = dBFamilyRepository.findById(dBFamily.getId()).get();
        // Disconnect from session so that the updates on updatedDBFamily are not directly saved in db
        em.detach(updatedDBFamily);
        updatedDBFamily
            .name(UPDATED_NAME);

        restDBFamilyMockMvc.perform(put("/api/db-families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDBFamily)))
            .andExpect(status().isOk());

        // Validate the DBFamily in the database
        List<DBFamily> dBFamilyList = dBFamilyRepository.findAll();
        assertThat(dBFamilyList).hasSize(databaseSizeBeforeUpdate);
        DBFamily testDBFamily = dBFamilyList.get(dBFamilyList.size() - 1);
        assertThat(testDBFamily.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDBFamily() throws Exception {
        int databaseSizeBeforeUpdate = dBFamilyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDBFamilyMockMvc.perform(put("/api/db-families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBFamily)))
            .andExpect(status().isBadRequest());

        // Validate the DBFamily in the database
        List<DBFamily> dBFamilyList = dBFamilyRepository.findAll();
        assertThat(dBFamilyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDBFamily() throws Exception {
        // Initialize the database
        dBFamilyService.save(dBFamily);

        int databaseSizeBeforeDelete = dBFamilyRepository.findAll().size();

        // Delete the dBFamily
        restDBFamilyMockMvc.perform(delete("/api/db-families/{id}", dBFamily.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DBFamily> dBFamilyList = dBFamilyRepository.findAll();
        assertThat(dBFamilyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
