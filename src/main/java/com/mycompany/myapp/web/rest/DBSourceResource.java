package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DBSource;
import com.mycompany.myapp.service.DBSourceService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DBSource}.
 */
@RestController
@RequestMapping("/api")
public class DBSourceResource {

    private final Logger log = LoggerFactory.getLogger(DBSourceResource.class);

    private static final String ENTITY_NAME = "dBSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DBSourceService dBSourceService;

    public DBSourceResource(DBSourceService dBSourceService) {
        this.dBSourceService = dBSourceService;
    }

    /**
     * {@code POST  /db-sources} : Create a new dBSource.
     *
     * @param dBSource the dBSource to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dBSource, or with status {@code 400 (Bad Request)} if the dBSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/db-sources")
    public ResponseEntity<DBSource> createDBSource(@Valid @RequestBody DBSource dBSource) throws URISyntaxException {
        log.debug("REST request to save DBSource : {}", dBSource);
        if (dBSource.getId() != null) {
            throw new BadRequestAlertException("A new dBSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DBSource result = dBSourceService.save(dBSource);
        return ResponseEntity.created(new URI("/api/db-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /db-sources} : Updates an existing dBSource.
     *
     * @param dBSource the dBSource to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dBSource,
     * or with status {@code 400 (Bad Request)} if the dBSource is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dBSource couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/db-sources")
    public ResponseEntity<DBSource> updateDBSource(@Valid @RequestBody DBSource dBSource) throws URISyntaxException {
        log.debug("REST request to update DBSource : {}", dBSource);
        if (dBSource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DBSource result = dBSourceService.save(dBSource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dBSource.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /db-sources} : get all the dBSources.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dBSources in body.
     */
    @GetMapping("/db-sources")
    public ResponseEntity<List<DBSource>> getAllDBSources(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of DBSources");
        Page<DBSource> page;
        if (eagerload) {
            page = dBSourceService.findAllWithEagerRelationships(pageable);
        } else {
            page = dBSourceService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /db-sources/:id} : get the "id" dBSource.
     *
     * @param id the id of the dBSource to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dBSource, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/db-sources/{id}")
    public ResponseEntity<DBSource> getDBSource(@PathVariable Long id) {
        log.debug("REST request to get DBSource : {}", id);
        Optional<DBSource> dBSource = dBSourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dBSource);
    }

    /**
     * {@code DELETE  /db-sources/:id} : delete the "id" dBSource.
     *
     * @param id the id of the dBSource to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/db-sources/{id}")
    public ResponseEntity<Void> deleteDBSource(@PathVariable Long id) {
        log.debug("REST request to delete DBSource : {}", id);
        dBSourceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
