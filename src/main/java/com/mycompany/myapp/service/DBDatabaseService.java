package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DBDatabase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DBDatabase}.
 */
public interface DBDatabaseService {

    /**
     * Save a dBDatabase.
     *
     * @param dBDatabase the entity to save.
     * @return the persisted entity.
     */
    DBDatabase save(DBDatabase dBDatabase);

    /**
     * Get all the dBDatabases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DBDatabase> findAll(Pageable pageable);

    /**
     * Get all the dBDatabases with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<DBDatabase> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" dBDatabase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DBDatabase> findOne(Long id);

    /**
     * Delete the "id" dBDatabase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
