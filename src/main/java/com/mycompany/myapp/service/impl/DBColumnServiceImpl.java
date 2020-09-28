package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DBColumnService;
import com.mycompany.myapp.domain.DBColumn;
import com.mycompany.myapp.repository.DBColumnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DBColumn}.
 */
@Service
@Transactional
public class DBColumnServiceImpl implements DBColumnService {

    private final Logger log = LoggerFactory.getLogger(DBColumnServiceImpl.class);

    private final DBColumnRepository dBColumnRepository;

    public DBColumnServiceImpl(DBColumnRepository dBColumnRepository) {
        this.dBColumnRepository = dBColumnRepository;
    }

    @Override
    public DBColumn save(DBColumn dBColumn) {
        log.debug("Request to save DBColumn : {}", dBColumn);
        return dBColumnRepository.save(dBColumn);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DBColumn> findAll(Pageable pageable) {
        log.debug("Request to get all DBColumns");
        return dBColumnRepository.findAll(pageable);
    }


    public Page<DBColumn> findAllWithEagerRelationships(Pageable pageable) {
        return dBColumnRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DBColumn> findOne(Long id) {
        log.debug("Request to get DBColumn : {}", id);
        return dBColumnRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DBColumn : {}", id);
        dBColumnRepository.deleteById(id);
    }
}
