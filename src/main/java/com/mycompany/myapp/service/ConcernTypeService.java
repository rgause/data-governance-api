package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ConcernType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ConcernType}.
 */
public interface ConcernTypeService {

    /**
     * Save a concernType.
     *
     * @param concernType the entity to save.
     * @return the persisted entity.
     */
    ConcernType save(ConcernType concernType);

    /**
     * Get all the concernTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConcernType> findAll(Pageable pageable);


    /**
     * Get the "id" concernType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConcernType> findOne(Long id);

    /**
     * Delete the "id" concernType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
