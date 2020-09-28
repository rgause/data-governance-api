package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DataGovernanceApiApp;
import com.mycompany.myapp.domain.DBSource;
import com.mycompany.myapp.repository.DBSourceRepository;
import com.mycompany.myapp.service.DBSourceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DBSourceResource} REST controller.
 */
@SpringBootTest(classes = DataGovernanceApiApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DBSourceResourceIT {

    private static final String DEFAULT_SOURCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_NAME = "BBBBBBBBBB";

    @Autowired
    private DBSourceRepository dBSourceRepository;

    @Mock
    private DBSourceRepository dBSourceRepositoryMock;

    @Mock
    private DBSourceService dBSourceServiceMock;

    @Autowired
    private DBSourceService dBSourceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDBSourceMockMvc;

    private DBSource dBSource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBSource createEntity(EntityManager em) {
        DBSource dBSource = new DBSource()
            .sourceName(DEFAULT_SOURCE_NAME);
        return dBSource;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBSource createUpdatedEntity(EntityManager em) {
        DBSource dBSource = new DBSource()
            .sourceName(UPDATED_SOURCE_NAME);
        return dBSource;
    }

    @BeforeEach
    public void initTest() {
        dBSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createDBSource() throws Exception {
        int databaseSizeBeforeCreate = dBSourceRepository.findAll().size();
        // Create the DBSource
        restDBSourceMockMvc.perform(post("/api/db-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBSource)))
            .andExpect(status().isCreated());

        // Validate the DBSource in the database
        List<DBSource> dBSourceList = dBSourceRepository.findAll();
        assertThat(dBSourceList).hasSize(databaseSizeBeforeCreate + 1);
        DBSource testDBSource = dBSourceList.get(dBSourceList.size() - 1);
        assertThat(testDBSource.getSourceName()).isEqualTo(DEFAULT_SOURCE_NAME);
    }

    @Test
    @Transactional
    public void createDBSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dBSourceRepository.findAll().size();

        // Create the DBSource with an existing ID
        dBSource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDBSourceMockMvc.perform(post("/api/db-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBSource)))
            .andExpect(status().isBadRequest());

        // Validate the DBSource in the database
        List<DBSource> dBSourceList = dBSourceRepository.findAll();
        assertThat(dBSourceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDBSources() throws Exception {
        // Initialize the database
        dBSourceRepository.saveAndFlush(dBSource);

        // Get all the dBSourceList
        restDBSourceMockMvc.perform(get("/api/db-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dBSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceName").value(hasItem(DEFAULT_SOURCE_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDBSourcesWithEagerRelationshipsIsEnabled() throws Exception {
        when(dBSourceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDBSourceMockMvc.perform(get("/api/db-sources?eagerload=true"))
            .andExpect(status().isOk());

        verify(dBSourceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDBSourcesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dBSourceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDBSourceMockMvc.perform(get("/api/db-sources?eagerload=true"))
            .andExpect(status().isOk());

        verify(dBSourceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDBSource() throws Exception {
        // Initialize the database
        dBSourceRepository.saveAndFlush(dBSource);

        // Get the dBSource
        restDBSourceMockMvc.perform(get("/api/db-sources/{id}", dBSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dBSource.getId().intValue()))
            .andExpect(jsonPath("$.sourceName").value(DEFAULT_SOURCE_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDBSource() throws Exception {
        // Get the dBSource
        restDBSourceMockMvc.perform(get("/api/db-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDBSource() throws Exception {
        // Initialize the database
        dBSourceService.save(dBSource);

        int databaseSizeBeforeUpdate = dBSourceRepository.findAll().size();

        // Update the dBSource
        DBSource updatedDBSource = dBSourceRepository.findById(dBSource.getId()).get();
        // Disconnect from session so that the updates on updatedDBSource are not directly saved in db
        em.detach(updatedDBSource);
        updatedDBSource
            .sourceName(UPDATED_SOURCE_NAME);

        restDBSourceMockMvc.perform(put("/api/db-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDBSource)))
            .andExpect(status().isOk());

        // Validate the DBSource in the database
        List<DBSource> dBSourceList = dBSourceRepository.findAll();
        assertThat(dBSourceList).hasSize(databaseSizeBeforeUpdate);
        DBSource testDBSource = dBSourceList.get(dBSourceList.size() - 1);
        assertThat(testDBSource.getSourceName()).isEqualTo(UPDATED_SOURCE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDBSource() throws Exception {
        int databaseSizeBeforeUpdate = dBSourceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDBSourceMockMvc.perform(put("/api/db-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBSource)))
            .andExpect(status().isBadRequest());

        // Validate the DBSource in the database
        List<DBSource> dBSourceList = dBSourceRepository.findAll();
        assertThat(dBSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDBSource() throws Exception {
        // Initialize the database
        dBSourceService.save(dBSource);

        int databaseSizeBeforeDelete = dBSourceRepository.findAll().size();

        // Delete the dBSource
        restDBSourceMockMvc.perform(delete("/api/db-sources/{id}", dBSource.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DBSource> dBSourceList = dBSourceRepository.findAll();
        assertThat(dBSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
