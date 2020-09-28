package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DBTable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DBTable}.
 */
public interface DBTableService {

    /**
     * Save a dBTable.
     *
     * @param dBTable the entity to save.
     * @return the persisted entity.
     */
    DBTable save(DBTable dBTable);

    /**
     * Get all the dBTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DBTable> findAll(Pageable pageable);

    /**
     * Get all the dBTables with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<DBTable> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" dBTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DBTable> findOne(Long id);

    /**
     * Delete the "id" dBTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
