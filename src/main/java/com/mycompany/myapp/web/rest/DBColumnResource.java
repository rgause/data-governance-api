package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DBColumn;
import com.mycompany.myapp.service.DBColumnService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DBColumn}.
 */
@RestController
@RequestMapping("/api")
public class DBColumnResource {

    private final Logger log = LoggerFactory.getLogger(DBColumnResource.class);

    private static final String ENTITY_NAME = "dBColumn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DBColumnService dBColumnService;

    public DBColumnResource(DBColumnService dBColumnService) {
        this.dBColumnService = dBColumnService;
    }

    /**
     * {@code POST  /db-columns} : Create a new dBColumn.
     *
     * @param dBColumn the dBColumn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dBColumn, or with status {@code 400 (Bad Request)} if the dBColumn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/db-columns")
    public ResponseEntity<DBColumn> createDBColumn(@Valid @RequestBody DBColumn dBColumn) throws URISyntaxException {
        log.debug("REST request to save DBColumn : {}", dBColumn);
        if (dBColumn.getId() != null) {
            throw new BadRequestAlertException("A new dBColumn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DBColumn result = dBColumnService.save(dBColumn);
        return ResponseEntity.created(new URI("/api/db-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /db-columns} : Updates an existing dBColumn.
     *
     * @param dBColumn the dBColumn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dBColumn,
     * or with status {@code 400 (Bad Request)} if the dBColumn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dBColumn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/db-columns")
    public ResponseEntity<DBColumn> updateDBColumn(@Valid @RequestBody DBColumn dBColumn) throws URISyntaxException {
        log.debug("REST request to update DBColumn : {}", dBColumn);
        if (dBColumn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DBColumn result = dBColumnService.save(dBColumn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dBColumn.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /db-columns} : get all the dBColumns.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dBColumns in body.
     */
    @GetMapping("/db-columns")
    public ResponseEntity<List<DBColumn>> getAllDBColumns(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of DBColumns");
        Page<DBColumn> page;
        if (eagerload) {
            page = dBColumnService.findAllWithEagerRelationships(pageable);
        } else {
            page = dBColumnService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /db-columns/:id} : get the "id" dBColumn.
     *
     * @param id the id of the dBColumn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dBColumn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/db-columns/{id}")
    public ResponseEntity<DBColumn> getDBColumn(@PathVariable Long id) {
        log.debug("REST request to get DBColumn : {}", id);
        Optional<DBColumn> dBColumn = dBColumnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dBColumn);
    }

    /**
     * {@code DELETE  /db-columns/:id} : delete the "id" dBColumn.
     *
     * @param id the id of the dBColumn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/db-columns/{id}")
    public ResponseEntity<Void> deleteDBColumn(@PathVariable Long id) {
        log.debug("REST request to delete DBColumn : {}", id);
        dBColumnService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
