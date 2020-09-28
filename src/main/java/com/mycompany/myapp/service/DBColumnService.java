package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DBColumn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DBColumn}.
 */
public interface DBColumnService {

    /**
     * Save a dBColumn.
     *
     * @param dBColumn the entity to save.
     * @return the persisted entity.
     */
    DBColumn save(DBColumn dBColumn);

    /**
     * Get all the dBColumns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DBColumn> findAll(Pageable pageable);

    /**
     * Get all the dBColumns with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<DBColumn> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" dBColumn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DBColumn> findOne(Long id);

    /**
     * Delete the "id" dBColumn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
