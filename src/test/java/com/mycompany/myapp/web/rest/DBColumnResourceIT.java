package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DataGovernanceApiApp;
import com.mycompany.myapp.domain.DBColumn;
import com.mycompany.myapp.repository.DBColumnRepository;
import com.mycompany.myapp.service.DBColumnService;

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
 * Integration tests for the {@link DBColumnResource} REST controller.
 */
@SpringBootTest(classes = DataGovernanceApiApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DBColumnResourceIT {

    private static final String DEFAULT_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_NAME = "BBBBBBBBBB";

    @Autowired
    private DBColumnRepository dBColumnRepository;

    @Mock
    private DBColumnRepository dBColumnRepositoryMock;

    @Mock
    private DBColumnService dBColumnServiceMock;

    @Autowired
    private DBColumnService dBColumnService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDBColumnMockMvc;

    private DBColumn dBColumn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBColumn createEntity(EntityManager em) {
        DBColumn dBColumn = new DBColumn()
            .columnName(DEFAULT_COLUMN_NAME);
        return dBColumn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DBColumn createUpdatedEntity(EntityManager em) {
        DBColumn dBColumn = new DBColumn()
            .columnName(UPDATED_COLUMN_NAME);
        return dBColumn;
    }

    @BeforeEach
    public void initTest() {
        dBColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createDBColumn() throws Exception {
        int databaseSizeBeforeCreate = dBColumnRepository.findAll().size();
        // Create the DBColumn
        restDBColumnMockMvc.perform(post("/api/db-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBColumn)))
            .andExpect(status().isCreated());

        // Validate the DBColumn in the database
        List<DBColumn> dBColumnList = dBColumnRepository.findAll();
        assertThat(dBColumnList).hasSize(databaseSizeBeforeCreate + 1);
        DBColumn testDBColumn = dBColumnList.get(dBColumnList.size() - 1);
        assertThat(testDBColumn.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void createDBColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dBColumnRepository.findAll().size();

        // Create the DBColumn with an existing ID
        dBColumn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDBColumnMockMvc.perform(post("/api/db-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBColumn)))
            .andExpect(status().isBadRequest());

        // Validate the DBColumn in the database
        List<DBColumn> dBColumnList = dBColumnRepository.findAll();
        assertThat(dBColumnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDBColumns() throws Exception {
        // Initialize the database
        dBColumnRepository.saveAndFlush(dBColumn);

        // Get all the dBColumnList
        restDBColumnMockMvc.perform(get("/api/db-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dBColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDBColumnsWithEagerRelationshipsIsEnabled() throws Exception {
        when(dBColumnServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDBColumnMockMvc.perform(get("/api/db-columns?eagerload=true"))
            .andExpect(status().isOk());

        verify(dBColumnServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDBColumnsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dBColumnServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDBColumnMockMvc.perform(get("/api/db-columns?eagerload=true"))
            .andExpect(status().isOk());

        verify(dBColumnServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDBColumn() throws Exception {
        // Initialize the database
        dBColumnRepository.saveAndFlush(dBColumn);

        // Get the dBColumn
        restDBColumnMockMvc.perform(get("/api/db-columns/{id}", dBColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dBColumn.getId().intValue()))
            .andExpect(jsonPath("$.columnName").value(DEFAULT_COLUMN_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDBColumn() throws Exception {
        // Get the dBColumn
        restDBColumnMockMvc.perform(get("/api/db-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDBColumn() throws Exception {
        // Initialize the database
        dBColumnService.save(dBColumn);

        int databaseSizeBeforeUpdate = dBColumnRepository.findAll().size();

        // Update the dBColumn
        DBColumn updatedDBColumn = dBColumnRepository.findById(dBColumn.getId()).get();
        // Disconnect from session so that the updates on updatedDBColumn are not directly saved in db
        em.detach(updatedDBColumn);
        updatedDBColumn
            .columnName(UPDATED_COLUMN_NAME);

        restDBColumnMockMvc.perform(put("/api/db-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDBColumn)))
            .andExpect(status().isOk());

        // Validate the DBColumn in the database
        List<DBColumn> dBColumnList = dBColumnRepository.findAll();
        assertThat(dBColumnList).hasSize(databaseSizeBeforeUpdate);
        DBColumn testDBColumn = dBColumnList.get(dBColumnList.size() - 1);
        assertThat(testDBColumn.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDBColumn() throws Exception {
        int databaseSizeBeforeUpdate = dBColumnRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDBColumnMockMvc.perform(put("/api/db-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dBColumn)))
            .andExpect(status().isBadRequest());

        // Validate the DBColumn in the database
        List<DBColumn> dBColumnList = dBColumnRepository.findAll();
        assertThat(dBColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDBColumn() throws Exception {
        // Initialize the database
        dBColumnService.save(dBColumn);

        int databaseSizeBeforeDelete = dBColumnRepository.findAll().size();

        // Delete the dBColumn
        restDBColumnMockMvc.perform(delete("/api/db-columns/{id}", dBColumn.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DBColumn> dBColumnList = dBColumnRepository.findAll();
        assertThat(dBColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
