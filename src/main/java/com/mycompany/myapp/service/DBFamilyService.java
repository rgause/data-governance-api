package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DBFamily;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DBFamily}.
 */
public interface DBFamilyService {

    /**
     * Save a dBFamily.
     *
     * @param dBFamily the entity to save.
     * @return the persisted entity.
     */
    DBFamily save(DBFamily dBFamily);

    /**
     * Get all the dBFamilies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DBFamily> findAll(Pageable pageable);


    /**
     * Get the "id" dBFamily.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DBFamily> findOne(Long id);

    /**
     * Delete the "id" dBFamily.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
