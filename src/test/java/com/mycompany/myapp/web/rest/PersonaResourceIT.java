package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DataGovernanceApiApp;
import com.mycompany.myapp.domain.Persona;
import com.mycompany.myapp.repository.PersonaRepository;
import com.mycompany.myapp.service.PersonaService;

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
 * Integration tests for the {@link PersonaResource} REST controller.
 */
@SpringBootTest(classes = DataGovernanceApiApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonaResourceIT {

    private static final String DEFAULT_PERSONA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECURITY_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECURITY_GROUP_NAME = "BBBBBBBBBB";

    @Autowired
    private PersonaRepository personaRepository;

    @Mock
    private PersonaRepository personaRepositoryMock;

    @Mock
    private PersonaService personaServiceMock;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonaMockMvc;

    private Persona persona;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persona createEntity(EntityManager em) {
        Persona persona = new Persona()
            .personaName(DEFAULT_PERSONA_NAME)
            .securityGroupName(DEFAULT_SECURITY_GROUP_NAME);
        return persona;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persona createUpdatedEntity(EntityManager em) {
        Persona persona = new Persona()
            .personaName(UPDATED_PERSONA_NAME)
            .securityGroupName(UPDATED_SECURITY_GROUP_NAME);
        return persona;
    }

    @BeforeEach
    public void initTest() {
        persona = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersona() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();
        // Create the Persona
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate + 1);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getPersonaName()).isEqualTo(DEFAULT_PERSONA_NAME);
        assertThat(testPersona.getSecurityGroupName()).isEqualTo(DEFAULT_SECURITY_GROUP_NAME);
    }

    @Test
    @Transactional
    public void createPersonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona with an existing ID
        persona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPersonas() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList
        restPersonaMockMvc.perform(get("/api/personas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].personaName").value(hasItem(DEFAULT_PERSONA_NAME)))
            .andExpect(jsonPath("$.[*].securityGroupName").value(hasItem(DEFAULT_SECURITY_GROUP_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPersonasWithEagerRelationshipsIsEnabled() throws Exception {
        when(personaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonaMockMvc.perform(get("/api/personas?eagerload=true"))
            .andExpect(status().isOk());

        verify(personaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPersonasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(personaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonaMockMvc.perform(get("/api/personas?eagerload=true"))
            .andExpect(status().isOk());

        verify(personaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", persona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(persona.getId().intValue()))
            .andExpect(jsonPath("$.personaName").value(DEFAULT_PERSONA_NAME))
            .andExpect(jsonPath("$.securityGroupName").value(DEFAULT_SECURITY_GROUP_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingPersona() throws Exception {
        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersona() throws Exception {
        // Initialize the database
        personaService.save(persona);

        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona
        Persona updatedPersona = personaRepository.findById(persona.getId()).get();
        // Disconnect from session so that the updates on updatedPersona are not directly saved in db
        em.detach(updatedPersona);
        updatedPersona
            .personaName(UPDATED_PERSONA_NAME)
            .securityGroupName(UPDATED_SECURITY_GROUP_NAME);

        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersona)))
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getPersonaName()).isEqualTo(UPDATED_PERSONA_NAME);
        assertThat(testPersona.getSecurityGroupName()).isEqualTo(UPDATED_SECURITY_GROUP_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersona() throws Exception {
        // Initialize the database
        personaService.save(persona);

        int databaseSizeBeforeDelete = personaRepository.findAll().size();

        // Delete the persona
        restPersonaMockMvc.perform(delete("/api/personas/{id}", persona.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
