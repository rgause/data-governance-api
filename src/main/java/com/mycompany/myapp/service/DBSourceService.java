package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DBSource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DBSource}.
 */
public interface DBSourceService {

    /**
     * Save a dBSource.
     *
     * @param dBSource the entity to save.
     * @return the persisted entity.
     */
    DBSource save(DBSource dBSource);

    /**
     * Get all the dBSources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DBSource> findAll(Pageable pageable);

    /**
     * Get all the dBSources with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<DBSource> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" dBSource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DBSource> findOne(Long id);

    /**
     * Delete the "id" dBSource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
