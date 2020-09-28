package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DataGovernanceApiApp;
import com.mycompany.myapp.domain.DBTable;
import com.mycompany.myapp.repository.DBTableRepository;
import com.mycompany.myapp.service.DBTableService;

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
 * Integration tests for the {@link DBTableResource} REST controller.
 */
@SpringBootTest(classes = DataGovernanceApiApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DBTableResourceIT {

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    @Autowired
    private DBTableRepository dBTableRepository;

    @Mock
    private DBTableRepository dBTableRepositoryMock;

    @Mock
    private DBTableService dBTableServiceMock;

    @Autowired
    private DBTableService dBTableService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDBTableMockMvc;

    private DBTable dBTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBTable createEntity(EntityManager em) {
        DBTable dBTable = new DBTable()
            .tableName(DEFAULT_TABLE_NAME);
        return dBTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBTable createUpdatedEntity(EntityManager em) {
        DBTable dBTable = new DBTable()
            .tableName(UPDATED_TABLE_NAME);
        return dBTable;
    }

    @BeforeEach
    public void initTest() {
        dBTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createDBTable() throws Exception {
        int databaseSizeBeforeCreate = dBTableRepository.findAll().size();
        // Create the DBTable
        restDBTableMockMvc.perform(post("/api/db-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBTable)))
            .andExpect(status().isCreated());

        // Validate the DBTable in the database
        List<DBTable> dBTableList = dBTableRepository.findAll();
        assertThat(dBTableList).hasSize(databaseSizeBeforeCreate + 1);
        DBTable testDBTable = dBTableList.get(dBTableList.size() - 1);
        assertThat(testDBTable.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
    }

    @Test
    @Transactional
    public void createDBTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dBTableRepository.findAll().size();

        // Create the DBTable with an existing ID
        dBTable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDBTableMockMvc.perform(post("/api/db-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBTable)))
            .andExpect(status().isBadRequest());

        // Validate the DBTable in the database
        List<DBTable> dBTableList = dBTableRepository.findAll();
        assertThat(dBTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDBTables() throws Exception {
        // Initialize the database
        dBTableRepository.saveAndFlush(dBTable);

        // Get all the dBTableList
        restDBTableMockMvc.perform(get("/api/db-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dBTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDBTablesWithEagerRelationshipsIsEnabled() throws Exception {
        when(dBTableServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDBTableMockMvc.perform(get("/api/db-tables?eagerload=true"))
            .andExpect(status().isOk());

        verify(dBTableServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDBTablesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dBTableServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDBTableMockMvc.perform(get("/api/db-tables?eagerload=true"))
            .andExpect(status().isOk());

        verify(dBTableServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDBTable() throws Exception {
        // Initialize the database
        dBTableRepository.saveAndFlush(dBTable);

        // Get the dBTable
        restDBTableMockMvc.perform(get("/api/db-tables/{id}", dBTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dBTable.getId().intValue()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDBTable() throws Exception {
        // Get the dBTable
        restDBTableMockMvc.perform(get("/api/db-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDBTable() throws Exception {
        // Initialize the database
        dBTableService.save(dBTable);

        int databaseSizeBeforeUpdate = dBTableRepository.findAll().size();

        // Update the dBTable
        DBTable updatedDBTable = dBTableRepository.findById(dBTable.getId()).get();
        // Disconnect from session so that the updates on updatedDBTable are not directly saved in db
        em.detach(updatedDBTable);
        updatedDBTable
            .tableName(UPDATED_TABLE_NAME);

        restDBTableMockMvc.perform(put("/api/db-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDBTable)))
            .andExpect(status().isOk());

        // Validate the DBTable in the database
        List<DBTable> dBTableList = dBTableRepository.findAll();
        assertThat(dBTableList).hasSize(databaseSizeBeforeUpdate);
        DBTable testDBTable = dBTableList.get(dBTableList.size() - 1);
        assertThat(testDBTable.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDBTable() throws Exception {
        int databaseSizeBeforeUpdate = dBTableRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDBTableMockMvc.perform(put("/api/db-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBTable)))
            .andExpect(status().isBadRequest());

        // Validate the DBTable in the database
        List<DBTable> dBTableList = dBTableRepository.findAll();
        assertThat(dBTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDBTable() throws Exception {
        // Initialize the database
        dBTableService.save(dBTable);

        int databaseSizeBeforeDelete = dBTableRepository.findAll().size();

        // Delete the dBTable
        restDBTableMockMvc.perform(delete("/api/db-tables/{id}", dBTable.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DBTable> dBTableList = dBTableRepository.findAll();
        assertThat(dBTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
