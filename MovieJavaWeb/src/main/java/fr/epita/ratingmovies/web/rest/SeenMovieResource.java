package fr.epita.ratingmovies.web.rest;

import fr.epita.ratingmovies.service.SeenMovieService;
import fr.epita.ratingmovies.web.rest.errors.BadRequestAlertException;
import fr.epita.ratingmovies.service.dto.SeenMovieDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.epita.ratingmovies.domain.SeenMovie}.
 */
@RestController
@RequestMapping("/api")
public class SeenMovieResource {

    private final Logger log = LoggerFactory.getLogger(SeenMovieResource.class);

    private static final String ENTITY_NAME = "seenMovie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeenMovieService seenMovieService;

    public SeenMovieResource(SeenMovieService seenMovieService) {
        this.seenMovieService = seenMovieService;
    }

    /**
     * {@code POST  /seen-movies} : Create a new seenMovie.
     *
     * @param seenMovieDTO the seenMovieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seenMovieDTO, or with status {@code 400 (Bad Request)} if the seenMovie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seen-movies")
    public ResponseEntity<SeenMovieDTO> createSeenMovie(@RequestBody SeenMovieDTO seenMovieDTO) throws URISyntaxException {
        log.debug("REST request to save SeenMovie : {}", seenMovieDTO);
        if (seenMovieDTO.getId() != null) {
            throw new BadRequestAlertException("A new seenMovie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeenMovieDTO result = seenMovieService.save(seenMovieDTO);
        return ResponseEntity.created(new URI("/api/seen-movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seen-movies} : Updates an existing seenMovie.
     *
     * @param seenMovieDTO the seenMovieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seenMovieDTO,
     * or with status {@code 400 (Bad Request)} if the seenMovieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seenMovieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seen-movies")
    public ResponseEntity<SeenMovieDTO> updateSeenMovie(@RequestBody SeenMovieDTO seenMovieDTO) throws URISyntaxException {
        log.debug("REST request to update SeenMovie : {}", seenMovieDTO);
        if (seenMovieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SeenMovieDTO result = seenMovieService.save(seenMovieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, seenMovieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /seen-movies} : get all the seenMovies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seenMovies in body.
     */
    @GetMapping("/seen-movies")
    public ResponseEntity<List<SeenMovieDTO>> getAllSeenMovies(Pageable pageable) {
        log.debug("REST request to get a page of SeenMovies");
        Page<SeenMovieDTO> page = seenMovieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seen-movies/:id} : get the "id" seenMovie.
     *
     * @param id the id of the seenMovieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seenMovieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seen-movies/{id}")
    public ResponseEntity<SeenMovieDTO> getSeenMovie(@PathVariable Long id) {
        log.debug("REST request to get SeenMovie : {}", id);
        Optional<SeenMovieDTO> seenMovieDTO = seenMovieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seenMovieDTO);
    }

    /**
     * {@code DELETE  /seen-movies/:id} : delete the "id" seenMovie.
     *
     * @param id the id of the seenMovieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seen-movies/{id}")
    public ResponseEntity<Void> deleteSeenMovie(@PathVariable Long id) {
        log.debug("REST request to delete SeenMovie : {}", id);
        seenMovieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
