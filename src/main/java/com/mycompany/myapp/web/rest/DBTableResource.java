package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DBTable;
import com.mycompany.myapp.service.DBTableService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DBTable}.
 */
@RestController
@RequestMapping("/api")
public class DBTableResource {

    private final Logger log = LoggerFactory.getLogger(DBTableResource.class);

    private static final String ENTITY_NAME = "dBTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DBTableService dBTableService;

    public DBTableResource(DBTableService dBTableService) {
        this.dBTableService = dBTableService;
    }

    /**
     * {@code POST  /db-tables} : Create a new dBTable.
     *
     * @param dBTable the dBTable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dBTable, or with status {@code 400 (Bad Request)} if the dBTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/db-tables")
    public ResponseEntity<DBTable> createDBTable(@Valid @RequestBody DBTable dBTable) throws URISyntaxException {
        log.debug("REST request to save DBTable : {}", dBTable);
        if (dBTable.getId() != null) {
            throw new BadRequestAlertException("A new dBTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DBTable result = dBTableService.save(dBTable);
        return ResponseEntity.created(new URI("/api/db-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /db-tables} : Updates an existing dBTable.
     *
     * @param dBTable the dBTable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dBTable,
     * or with status {@code 400 (Bad Request)} if the dBTable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dBTable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/db-tables")
    public ResponseEntity<DBTable> updateDBTable(@Valid @RequestBody DBTable dBTable) throws URISyntaxException {
        log.debug("REST request to update DBTable : {}", dBTable);
        if (dBTable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DBTable result = dBTableService.save(dBTable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dBTable.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /db-tables} : get all the dBTables.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dBTables in body.
     */
    @GetMapping("/db-tables")
    public ResponseEntity<List<DBTable>> getAllDBTables(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of DBTables");
        Page<DBTable> page;
        if (eagerload) {
            page = dBTableService.findAllWithEagerRelationships(pageable);
        } else {
            page = dBTableService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /db-tables/:id} : get the "id" dBTable.
     *
     * @param id the id of the dBTable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dBTable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/db-tables/{id}")
    public ResponseEntity<DBTable> getDBTable(@PathVariable Long id) {
        log.debug("REST request to get DBTable : {}", id);
        Optional<DBTable> dBTable = dBTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dBTable);
    }

    /**
     * {@code DELETE  /db-tables/:id} : delete the "id" dBTable.
     *
     * @param id the id of the dBTable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/db-tables/{id}")
    public ResponseEntity<Void> deleteDBTable(@PathVariable Long id) {
        log.debug("REST request to delete DBTable : {}", id);
        dBTableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
