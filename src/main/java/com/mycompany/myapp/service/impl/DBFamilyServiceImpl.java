package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DBFamilyService;
import com.mycompany.myapp.domain.DBFamily;
import com.mycompany.myapp.repository.DBFamilyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DBFamily}.
 */
@Service
@Transactional
public class DBFamilyServiceImpl implements DBFamilyService {

    private final Logger log = LoggerFactory.getLogger(DBFamilyServiceImpl.class);

    private final DBFamilyRepository dBFamilyRepository;

    public DBFamilyServiceImpl(DBFamilyRepository dBFamilyRepository) {
        this.dBFamilyRepository = dBFamilyRepository;
    }

    @Override
    public DBFamily save(DBFamily dBFamily) {
        log.debug("Request to save DBFamily : {}", dBFamily);
        return dBFamilyRepository.save(dBFamily);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DBFamily> findAll(Pageable pageable) {
        log.debug("Request to get all DBFamilies");
        return dBFamilyRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DBFamily> findOne(Long id) {
        log.debug("Request to get DBFamily : {}", id);
        return dBFamilyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DBFamily : {}", id);
        dBFamilyRepository.deleteById(id);
    }
}
