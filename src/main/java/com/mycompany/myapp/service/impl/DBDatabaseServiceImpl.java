package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DBDatabaseService;
import com.mycompany.myapp.domain.DBDatabase;
import com.mycompany.myapp.repository.DBDatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DBDatabase}.
 */
@Service
@Transactional
public class DBDatabaseServiceImpl implements DBDatabaseService {

    private final Logger log = LoggerFactory.getLogger(DBDatabaseServiceImpl.class);

    private final DBDatabaseRepository dBDatabaseRepository;

    public DBDatabaseServiceImpl(DBDatabaseRepository dBDatabaseRepository) {
        this.dBDatabaseRepository = dBDatabaseRepository;
    }

    @Override
    public DBDatabase save(DBDatabase dBDatabase) {
        log.debug("Request to save DBDatabase : {}", dBDatabase);
        return dBDatabaseRepository.save(dBDatabase);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DBDatabase> findAll(Pageable pageable) {
        log.debug("Request to get all DBDatabases");
        return dBDatabaseRepository.findAll(pageable);
    }


    public Page<DBDatabase> findAllWithEagerRelationships(Pageable pageable) {
        return dBDatabaseRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DBDatabase> findOne(Long id) {
        log.debug("Request to get DBDatabase : {}", id);
        return dBDatabaseRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DBDatabase : {}", id);
        dBDatabaseRepository.deleteById(id);
    }
}
