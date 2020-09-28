package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Concern;
import com.mycompany.myapp.service.ConcernService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Concern}.
 */
@RestController
@RequestMapping("/api")
public class ConcernResource {

    private final Logger log = LoggerFactory.getLogger(ConcernResource.class);

    private static final String ENTITY_NAME = "concern";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcernService concernService;

    public ConcernResource(ConcernService concernService) {
        this.concernService = concernService;
    }

    /**
     * {@code POST  /concerns} : Create a new concern.
     *
     * @param concern the concern to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concern, or with status {@code 400 (Bad Request)} if the concern has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concerns")
    public ResponseEntity<Concern> createConcern(@Valid @RequestBody Concern concern) throws URISyntaxException {
        log.debug("REST request to save Concern : {}", concern);
        if (concern.getId() != null) {
            throw new BadRequestAlertException("A new concern cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Concern result = concernService.save(concern);
        return ResponseEntity.created(new URI("/api/concerns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concerns} : Updates an existing concern.
     *
     * @param concern the concern to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concern,
     * or with status {@code 400 (Bad Request)} if the concern is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concern couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concerns")
    public ResponseEntity<Concern> updateConcern(@Valid @RequestBody Concern concern) throws URISyntaxException {
        log.debug("REST request to update Concern : {}", concern);
        if (concern.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Concern result = concernService.save(concern);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, concern.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concerns} : get all the concerns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concerns in body.
     */
    @GetMapping("/concerns")
    public ResponseEntity<List<Concern>> getAllConcerns(Pageable pageable) {
        log.debug("REST request to get a page of Concerns");
        Page<Concern> page = concernService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /concerns/:id} : get the "id" concern.
     *
     * @param id the id of the concern to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concern, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concerns/{id}")
    public ResponseEntity<Concern> getConcern(@PathVariable Long id) {
        log.debug("REST request to get Concern : {}", id);
        Optional<Concern> concern = concernService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concern);
    }

    /**
     * {@code DELETE  /concerns/:id} : delete the "id" concern.
     *
     * @param id the id of the concern to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concerns/{id}")
    public ResponseEntity<Void> deleteConcern(@PathVariable Long id) {
        log.debug("REST request to delete Concern : {}", id);
        concernService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
