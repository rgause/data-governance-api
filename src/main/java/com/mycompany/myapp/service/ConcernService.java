package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Concern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Concern}.
 */
public interface ConcernService {

    /**
     * Save a concern.
     *
     * @param concern the entity to save.
     * @return the persisted entity.
     */
    Concern save(Concern concern);

    /**
     * Get all the concerns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Concern> findAll(Pageable pageable);


    /**
     * Get the "id" concern.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Concern> findOne(Long id);

    /**
     * Delete the "id" concern.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
