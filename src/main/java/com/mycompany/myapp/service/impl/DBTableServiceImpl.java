package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DBTableService;
import com.mycompany.myapp.domain.DBTable;
import com.mycompany.myapp.repository.DBTableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DBTable}.
 */
@Service
@Transactional
public class DBTableServiceImpl implements DBTableService {

    private final Logger log = LoggerFactory.getLogger(DBTableServiceImpl.class);

    private final DBTableRepository dBTableRepository;

    public DBTableServiceImpl(DBTableRepository dBTableRepository) {
        this.dBTableRepository = dBTableRepository;
    }

    @Override
    public DBTable save(DBTable dBTable) {
        log.debug("Request to save DBTable : {}", dBTable);
        return dBTableRepository.save(dBTable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DBTable> findAll(Pageable pageable) {
        log.debug("Request to get all DBTables");
        return dBTableRepository.findAll(pageable);
    }


    public Page<DBTable> findAllWithEagerRelationships(Pageable pageable) {
        return dBTableRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DBTable> findOne(Long id) {
        log.debug("Request to get DBTable : {}", id);
        return dBTableRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DBTable : {}", id);
        dBTableRepository.deleteById(id);
    }
}
