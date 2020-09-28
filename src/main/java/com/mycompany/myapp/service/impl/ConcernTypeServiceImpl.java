package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ConcernTypeService;
import com.mycompany.myapp.domain.ConcernType;
import com.mycompany.myapp.repository.ConcernTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConcernType}.
 */
@Service
@Transactional
public class ConcernTypeServiceImpl implements ConcernTypeService {

    private final Logger log = LoggerFactory.getLogger(ConcernTypeServiceImpl.class);

    private final ConcernTypeRepository concernTypeRepository;

    public ConcernTypeServiceImpl(ConcernTypeRepository concernTypeRepository) {
        this.concernTypeRepository = concernTypeRepository;
    }

    @Override
    public ConcernType save(ConcernType concernType) {
        log.debug("Request to save ConcernType : {}", concernType);
        return concernTypeRepository.save(concernType);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConcernType> findAll(Pageable pageable) {
        log.debug("Request to get all ConcernTypes");
        return concernTypeRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ConcernType> findOne(Long id) {
        log.debug("Request to get ConcernType : {}", id);
        return concernTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConcernType : {}", id);
        concernTypeRepository.deleteById(id);
    }
}
