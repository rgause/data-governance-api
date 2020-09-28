package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DBDatabase;
import com.mycompany.myapp.service.DBDatabaseService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DBDatabase}.
 */
@RestController
@RequestMapping("/api")
public class DBDatabaseResource {

    private final Logger log = LoggerFactory.getLogger(DBDatabaseResource.class);

    private static final String ENTITY_NAME = "dBDatabase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DBDatabaseService dBDatabaseService;

    public DBDatabaseResource(DBDatabaseService dBDatabaseService) {
        this.dBDatabaseService = dBDatabaseService;
    }

    /**
     * {@code POST  /db-databases} : Create a new dBDatabase.
     *
     * @param dBDatabase the dBDatabase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dBDatabase, or with status {@code 400 (Bad Request)} if the dBDatabase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/db-databases")
    public ResponseEntity<DBDatabase> createDBDatabase(@Valid @RequestBody DBDatabase dBDatabase) throws URISyntaxException {
        log.debug("REST request to save DBDatabase : {}", dBDatabase);
        if (dBDatabase.getId() != null) {
            throw new BadRequestAlertException("A new dBDatabase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DBDatabase result = dBDatabaseService.save(dBDatabase);
        return ResponseEntity.created(new URI("/api/db-databases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /db-databases} : Updates an existing dBDatabase.
     *
     * @param dBDatabase the dBDatabase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dBDatabase,
     * or with status {@code 400 (Bad Request)} if the dBDatabase is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dBDatabase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/db-databases")
    public ResponseEntity<DBDatabase> updateDBDatabase(@Valid @RequestBody DBDatabase dBDatabase) throws URISyntaxException {
        log.debug("REST request to update DBDatabase : {}", dBDatabase);
        if (dBDatabase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DBDatabase result = dBDatabaseService.save(dBDatabase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dBDatabase.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /db-databases} : get all the dBDatabases.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dBDatabases in body.
     */
    @GetMapping("/db-databases")
    public ResponseEntity<List<DBDatabase>> getAllDBDatabases(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of DBDatabases");
        Page<DBDatabase> page;
        if (eagerload) {
            page = dBDatabaseService.findAllWithEagerRelationships(pageable);
        } else {
            page = dBDatabaseService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /db-databases/:id} : get the "id" dBDatabase.
     *
     * @param id the id of the dBDatabase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dBDatabase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/db-databases/{id}")
    public ResponseEntity<DBDatabase> getDBDatabase(@PathVariable Long id) {
        log.debug("REST request to get DBDatabase : {}", id);
        Optional<DBDatabase> dBDatabase = dBDatabaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dBDatabase);
    }

    /**
     * {@code DELETE  /db-databases/:id} : delete the "id" dBDatabase.
     *
     * @param id the id of the dBDatabase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/db-databases/{id}")
    public ResponseEntity<Void> deleteDBDatabase(@PathVariable Long id) {
        log.debug("REST request to delete DBDatabase : {}", id);
        dBDatabaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
