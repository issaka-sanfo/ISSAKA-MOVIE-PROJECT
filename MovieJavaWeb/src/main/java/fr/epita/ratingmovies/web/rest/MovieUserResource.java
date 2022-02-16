package fr.epita.ratingmovies.web.rest;

import fr.epita.ratingmovies.service.MovieUserService;
import fr.epita.ratingmovies.web.rest.errors.BadRequestAlertException;
import fr.epita.ratingmovies.service.dto.MovieUserDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link fr.epita.ratingmovies.domain.MovieUser}.
 */
@RestController
@RequestMapping("/api")
public class MovieUserResource {

    private final Logger log = LoggerFactory.getLogger(MovieUserResource.class);

    private static final String ENTITY_NAME = "movieUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieUserService movieUserService;

    public MovieUserResource(MovieUserService movieUserService) {
        this.movieUserService = movieUserService;
    }

    /**
     * {@code POST  /movie-users} : Create a new movieUser.
     *
     * @param movieUserDTO the movieUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieUserDTO, or with status {@code 400 (Bad Request)} if the movieUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movie-users")
    public ResponseEntity<MovieUserDTO> createMovieUser(@RequestBody MovieUserDTO movieUserDTO) throws URISyntaxException {
        log.debug("REST request to save MovieUser : {}", movieUserDTO);
        if (movieUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieUserDTO result = movieUserService.save(movieUserDTO);
        return ResponseEntity.created(new URI("/api/movie-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movie-users} : Updates an existing movieUser.
     *
     * @param movieUserDTO the movieUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieUserDTO,
     * or with status {@code 400 (Bad Request)} if the movieUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movie-users")
    public ResponseEntity<MovieUserDTO> updateMovieUser(@RequestBody MovieUserDTO movieUserDTO) throws URISyntaxException {
        log.debug("REST request to update MovieUser : {}", movieUserDTO);
        if (movieUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieUserDTO result = movieUserService.save(movieUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movieUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movie-users} : get all the movieUsers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movieUsers in body.
     */
    @GetMapping("/movie-users")
    public ResponseEntity<List<MovieUserDTO>> getAllMovieUsers(Pageable pageable, @RequestParam(required = false) String filter, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("contact-is-null".equals(filter)) {
            log.debug("REST request to get all MovieUsers where contact is null");
            return new ResponseEntity<>(movieUserService.findAllWhereContactIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of MovieUsers");
        Page<MovieUserDTO> page;
        if (eagerload) {
            page = movieUserService.findAllWithEagerRelationships(pageable);
        } else {
            page = movieUserService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /movie-users/:id} : get the "id" movieUser.
     *
     * @param id the id of the movieUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movie-users/{id}")
    public ResponseEntity<MovieUserDTO> getMovieUser(@PathVariable Long id) {
        log.debug("REST request to get MovieUser : {}", id);
        Optional<MovieUserDTO> movieUserDTO = movieUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieUserDTO);
    }

    /**
     * {@code DELETE  /movie-users/:id} : delete the "id" movieUser.
     *
     * @param id the id of the movieUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movie-users/{id}")
    public ResponseEntity<Void> deleteMovieUser(@PathVariable Long id) {
        log.debug("REST request to delete MovieUser : {}", id);
        movieUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
