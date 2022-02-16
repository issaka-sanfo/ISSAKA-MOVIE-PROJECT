package fr.epita.ratingmovies.web.rest;

import fr.epita.ratingmovies.domain.Movie;
import fr.epita.ratingmovies.domain.MovieUser;
import fr.epita.ratingmovies.domain.SeenMovie;
import fr.epita.ratingmovies.repository.MovieRepository;
import fr.epita.ratingmovies.repository.MovieUserRepository;
import fr.epita.ratingmovies.repository.SeenMovieRepository;
import fr.epita.ratingmovies.security.SecurityUtils;
import fr.epita.ratingmovies.service.MovieService;
import fr.epita.ratingmovies.service.dto.UserDTO;
import fr.epita.ratingmovies.web.rest.errors.BadRequestAlertException;
import fr.epita.ratingmovies.service.dto.MovieDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.NormalizeNewlines;
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
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.epita.ratingmovies.domain.Movie}.
 */
@RestController
@RequestMapping("/api")
public class MovieResource {
    private final SeenMovieRepository seenMovieRepository;
    private final MovieUserRepository movieUserRepository;
    private AccountResource accountResource;
    private final MovieRepository movieRepository;
    private final Logger log = LoggerFactory.getLogger(MovieResource.class);


    private static final String ENTITY_NAME = "movie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovieService movieService;

    public MovieResource(SeenMovieRepository seenMovieRepository, MovieUserRepository movieUserRepository, AccountResource accountResource, MovieRepository movieRepository, MovieService movieService) {
        this.seenMovieRepository = seenMovieRepository;
        this.movieUserRepository = movieUserRepository;
        this.accountResource = accountResource;
        this.movieRepository = movieRepository;
        this.movieService = movieService;
    }

    /**
     * {@code POST  /movies} : Create a new movie.
     *
     * @param movieDTO the movieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movieDTO, or with status {@code 400 (Bad Request)} if the movie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) throws URISyntaxException {
        log.debug("REST request to save Movie : {}", movieDTO);
        if (movieDTO.getId() != null) {
            throw new BadRequestAlertException("A new movie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieDTO result = movieService.save(movieDTO);
        return ResponseEntity.created(new URI("/api/movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movies} : Updates an existing movie.
     *
     * @param movieDTO the movieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieDTO,
     * or with status {@code 400 (Bad Request)} if the movieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movies")
    public ResponseEntity<MovieDTO> updateMovie(@RequestBody MovieDTO movieDTO) throws URISyntaxException {
        log.debug("REST request to update Movie : {}", movieDTO);
        if (movieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieDTO result = movieService.save(movieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movies} : get all the movies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movies in body.
     */
    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> getAllMovies(Pageable pageable) {
        log.debug("REST request to get a page of Movies");
        if (SecurityUtils.getCurrentUserLogin().isPresent()) {
            Page<MovieDTO> page = movieService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
        return null;
    }

    /**
     * {@code GET  /movies/:id} : get the "id" movie.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movies/last")
    public ResponseEntity<MovieDTO> getLastMovie() {
        if (SecurityUtils.getCurrentUserLogin().isPresent()){
            UserDTO UserDTO = accountResource.getAccount();
            log.debug("************************************************************************************************User: {}", UserDTO.getLogin());
            MovieUser movieUser = movieUserRepository.findByUsername(UserDTO.getLogin());
            log.debug("************************************************************************************************MovieUser: {}",movieUser.getUsername());
            if (movieUser.getUsername() != null){
                try {
                    SeenMovie seenMovie =  seenMovieRepository.findFirstByMovieUserIdOrderByDateDesc(movieUser.getId());
                    log.debug("************************************************************************************************Seen: {}", seenMovie.getId());
                    Optional<MovieDTO> movieDTO = movieService.findOne(seenMovie.getMovie().getId());
                    log.debug("************************************************************************************************Movie: {}", movieDTO.get().getId());
                    return ResponseUtil.wrapOrNotFound(movieDTO);
                }catch (Exception e){
                    log.debug("******************************************************** User Exception: {}",e);
                    return null;
                }

            }
        }

        return null;
    }



    /**
     * {@code GET  /movies/:id} : get the "id" movie.
     *
     * @param id the id of the movieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable Long id) {
        log.debug("REST request to get Movie : {}", id);
        Optional<MovieDTO> movieDTO = movieService.findOne(id);

        UserDTO UserDTO = accountResource.getAccount();
        MovieUser movieUser = movieUserRepository.findByUsername(UserDTO.getLogin());
        SeenMovie seenMovie = new SeenMovie();
        Movie movie = movieRepository.findOneById(id);
        seenMovie.setDate(ZonedDateTime.now());
        seenMovie.setMovie(movie);
        seenMovie.setMovieUser(movieUser);
        seenMovieRepository.save(seenMovie);

        return ResponseUtil.wrapOrNotFound(movieDTO);
    }

    /**
     * {@code DELETE  /movies/:id} : delete the "id" movie.
     *
     * @param id the id of the movieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.debug("REST request to delete Movie : {}", id);
        movieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
