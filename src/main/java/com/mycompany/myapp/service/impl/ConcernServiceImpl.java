package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ConcernService;
import com.mycompany.myapp.domain.Concern;
import com.mycompany.myapp.repository.ConcernRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Concern}.
 */
@Service
@Transactional
public class ConcernServiceImpl implements ConcernService {

    private final Logger log = LoggerFactory.getLogger(ConcernServiceImpl.class);

    private final ConcernRepository concernRepository;

    public ConcernServiceImpl(ConcernRepository concernRepository) {
        this.concernRepository = concernRepository;
    }

    @Override
    public Concern save(Concern concern) {
        log.debug("Request to save Concern : {}", concern);
        return concernRepository.save(concern);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Concern> findAll(Pageable pageable) {
        log.debug("Request to get all Concerns");
        return concernRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Concern> findOne(Long id) {
        log.debug("Request to get Concern : {}", id);
        return concernRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Concern : {}", id);
        concernRepository.deleteById(id);
    }
}
