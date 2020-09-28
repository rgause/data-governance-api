package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DBSourceService;
import com.mycompany.myapp.domain.DBSource;
import com.mycompany.myapp.repository.DBSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DBSource}.
 */
@Service
@Transactional
public class DBSourceServiceImpl implements DBSourceService {

    private final Logger log = LoggerFactory.getLogger(DBSourceServiceImpl.class);

    private final DBSourceRepository dBSourceRepository;

    public DBSourceServiceImpl(DBSourceRepository dBSourceRepository) {
        this.dBSourceRepository = dBSourceRepository;
    }

    @Override
    public DBSource save(DBSource dBSource) {
        log.debug("Request to save DBSource : {}", dBSource);
        return dBSourceRepository.save(dBSource);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DBSource> findAll(Pageable pageable) {
        log.debug("Request to get all DBSources");
        return dBSourceRepository.findAll(pageable);
    }


    public Page<DBSource> findAllWithEagerRelationships(Pageable pageable) {
        return dBSourceRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DBSource> findOne(Long id) {
        log.debug("Request to get DBSource : {}", id);
        return dBSourceRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DBSource : {}", id);
        dBSourceRepository.deleteById(id);
    }
}
