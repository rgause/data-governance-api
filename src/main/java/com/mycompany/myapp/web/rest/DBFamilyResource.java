package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DBFamily;
import com.mycompany.myapp.service.DBFamilyService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DBFamily}.
 */
@RestController
@RequestMapping("/api")
public class DBFamilyResource {

    private final Logger log = LoggerFactory.getLogger(DBFamilyResource.class);

    private static final String ENTITY_NAME = "dBFamily";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DBFamilyService dBFamilyService;

    public DBFamilyResource(DBFamilyService dBFamilyService) {
        this.dBFamilyService = dBFamilyService;
    }

    /**
     * {@code POST  /db-families} : Create a new dBFamily.
     *
     * @param dBFamily the dBFamily to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dBFamily, or with status {@code 400 (Bad Request)} if the dBFamily has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/db-families")
    public ResponseEntity<DBFamily> createDBFamily(@Valid @RequestBody DBFamily dBFamily) throws URISyntaxException {
        log.debug("REST request to save DBFamily : {}", dBFamily);
        if (dBFamily.getId() != null) {
            throw new BadRequestAlertException("A new dBFamily cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DBFamily result = dBFamilyService.save(dBFamily);
        return ResponseEntity.created(new URI("/api/db-families/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /db-families} : Updates an existing dBFamily.
     *
     * @param dBFamily the dBFamily to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dBFamily,
     * or with status {@code 400 (Bad Request)} if the dBFamily is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dBFamily couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/db-families")
    public ResponseEntity<DBFamily> updateDBFamily(@Valid @RequestBody DBFamily dBFamily) throws URISyntaxException {
        log.debug("REST request to update DBFamily : {}", dBFamily);
        if (dBFamily.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DBFamily result = dBFamilyService.save(dBFamily);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dBFamily.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /db-families} : get all the dBFamilies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dBFamilies in body.
     */
    @GetMapping("/db-families")
    public ResponseEntity<List<DBFamily>> getAllDBFamilies(Pageable pageable) {
        log.debug("REST request to get a page of DBFamilies");
        Page<DBFamily> page = dBFamilyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /db-families/:id} : get the "id" dBFamily.
     *
     * @param id the id of the dBFamily to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dBFamily, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/db-families/{id}")
    public ResponseEntity<DBFamily> getDBFamily(@PathVariable Long id) {
        log.debug("REST request to get DBFamily : {}", id);
        Optional<DBFamily> dBFamily = dBFamilyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dBFamily);
    }

    /**
     * {@code DELETE  /db-families/:id} : delete the "id" dBFamily.
     *
     * @param id the id of the dBFamily to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/db-families/{id}")
    public ResponseEntity<Void> deleteDBFamily(@PathVariable Long id) {
        log.debug("REST request to delete DBFamily : {}", id);
        dBFamilyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
