package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DataGovernanceApiApp;
import com.mycompany.myapp.domain.DBDatabase;
import com.mycompany.myapp.repository.DBDatabaseRepository;
import com.mycompany.myapp.service.DBDatabaseService;

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
 * Integration tests for the {@link DBDatabaseResource} REST controller.
 */
@SpringBootTest(classes = DataGovernanceApiApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DBDatabaseResourceIT {

    private static final String DEFAULT_DATABASE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE_NAME = "BBBBBBBBBB";

    @Autowired
    private DBDatabaseRepository dBDatabaseRepository;

    @Mock
    private DBDatabaseRepository dBDatabaseRepositoryMock;

    @Mock
    private DBDatabaseService dBDatabaseServiceMock;

    @Autowired
    private DBDatabaseService dBDatabaseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDBDatabaseMockMvc;

    private DBDatabase dBDatabase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBDatabase createEntity(EntityManager em) {
        DBDatabase dBDatabase = new DBDatabase()
            .databaseName(DEFAULT_DATABASE_NAME);
        return dBDatabase;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBDatabase createUpdatedEntity(EntityManager em) {
        DBDatabase dBDatabase = new DBDatabase()
            .databaseName(UPDATED_DATABASE_NAME);
        return dBDatabase;
    }

    @BeforeEach
    public void initTest() {
        dBDatabase = createEntity(em);
    }

    @Test
    @Transactional
    public void createDBDatabase() throws Exception {
        int databaseSizeBeforeCreate = dBDatabaseRepository.findAll().size();
        // Create the DBDatabase
        restDBDatabaseMockMvc.perform(post("/api/db-databases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBDatabase)))
            .andExpect(status().isCreated());

        // Validate the DBDatabase in the database
        List<DBDatabase> dBDatabaseList = dBDatabaseRepository.findAll();
        assertThat(dBDatabaseList).hasSize(databaseSizeBeforeCreate + 1);
        DBDatabase testDBDatabase = dBDatabaseList.get(dBDatabaseList.size() - 1);
        assertThat(testDBDatabase.getDatabaseName()).isEqualTo(DEFAULT_DATABASE_NAME);
    }

    @Test
    @Transactional
    public void createDBDatabaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dBDatabaseRepository.findAll().size();

        // Create the DBDatabase with an existing ID
        dBDatabase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDBDatabaseMockMvc.perform(post("/api/db-databases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBDatabase)))
            .andExpect(status().isBadRequest());

        // Validate the DBDatabase in the database
        List<DBDatabase> dBDatabaseList = dBDatabaseRepository.findAll();
        assertThat(dBDatabaseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDBDatabases() throws Exception {
        // Initialize the database
        dBDatabaseRepository.saveAndFlush(dBDatabase);

        // Get all the dBDatabaseList
        restDBDatabaseMockMvc.perform(get("/api/db-databases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dBDatabase.getId().intValue())))
            .andExpect(jsonPath("$.[*].databaseName").value(hasItem(DEFAULT_DATABASE_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDBDatabasesWithEagerRelationshipsIsEnabled() throws Exception {
        when(dBDatabaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDBDatabaseMockMvc.perform(get("/api/db-databases?eagerload=true"))
            .andExpect(status().isOk());

        verify(dBDatabaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDBDatabasesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dBDatabaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDBDatabaseMockMvc.perform(get("/api/db-databases?eagerload=true"))
            .andExpect(status().isOk());

        verify(dBDatabaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDBDatabase() throws Exception {
        // Initialize the database
        dBDatabaseRepository.saveAndFlush(dBDatabase);

        // Get the dBDatabase
        restDBDatabaseMockMvc.perform(get("/api/db-databases/{id}", dBDatabase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dBDatabase.getId().intValue()))
            .andExpect(jsonPath("$.databaseName").value(DEFAULT_DATABASE_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDBDatabase() throws Exception {
        // Get the dBDatabase
        restDBDatabaseMockMvc.perform(get("/api/db-databases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDBDatabase() throws Exception {
        // Initialize the database
        dBDatabaseService.save(dBDatabase);

        int databaseSizeBeforeUpdate = dBDatabaseRepository.findAll().size();

        // Update the dBDatabase
        DBDatabase updatedDBDatabase = dBDatabaseRepository.findById(dBDatabase.getId()).get();
        // Disconnect from session so that the updates on updatedDBDatabase are not directly saved in db
        em.detach(updatedDBDatabase);
        updatedDBDatabase
            .databaseName(UPDATED_DATABASE_NAME);

        restDBDatabaseMockMvc.perform(put("/api/db-databases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDBDatabase)))
            .andExpect(status().isOk());

        // Validate the DBDatabase in the database
        List<DBDatabase> dBDatabaseList = dBDatabaseRepository.findAll();
        assertThat(dBDatabaseList).hasSize(databaseSizeBeforeUpdate);
        DBDatabase testDBDatabase = dBDatabaseList.get(dBDatabaseList.size() - 1);
        assertThat(testDBDatabase.getDatabaseName()).isEqualTo(UPDATED_DATABASE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDBDatabase() throws Exception {
        int databaseSizeBeforeUpdate = dBDatabaseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDBDatabaseMockMvc.perform(put("/api/db-databases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBDatabase)))
            .andExpect(status().isBadRequest());

        // Validate the DBDatabase in the database
        List<DBDatabase> dBDatabaseList = dBDatabaseRepository.findAll();
        assertThat(dBDatabaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDBDatabase() throws Exception {
        // Initialize the database
        dBDatabaseService.save(dBDatabase);

        int databaseSizeBeforeDelete = dBDatabaseRepository.findAll().size();

        // Delete the dBDatabase
        restDBDatabaseMockMvc.perform(delete("/api/db-databases/{id}", dBDatabase.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DBDatabase> dBDatabaseList = dBDatabaseRepository.findAll();
        assertThat(dBDatabaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
