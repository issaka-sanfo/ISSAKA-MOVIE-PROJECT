package fr.epita.ratingmovies.web.rest;

import fr.epita.ratingmovies.MovieJavaWebApp;
import fr.epita.ratingmovies.domain.SeenMovie;
import fr.epita.ratingmovies.repository.SeenMovieRepository;
import fr.epita.ratingmovies.service.SeenMovieService;
import fr.epita.ratingmovies.service.dto.SeenMovieDTO;
import fr.epita.ratingmovies.service.mapper.SeenMovieMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static fr.epita.ratingmovies.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SeenMovieResource} REST controller.
 */
@SpringBootTest(classes = MovieJavaWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SeenMovieResourceIT {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SeenMovieRepository seenMovieRepository;

    @Autowired
    private SeenMovieMapper seenMovieMapper;

    @Autowired
    private SeenMovieService seenMovieService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeenMovieMockMvc;

    private SeenMovie seenMovie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeenMovie createEntity(EntityManager em) {
        SeenMovie seenMovie = new SeenMovie()
            .date(DEFAULT_DATE);
        return seenMovie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeenMovie createUpdatedEntity(EntityManager em) {
        SeenMovie seenMovie = new SeenMovie()
            .date(UPDATED_DATE);
        return seenMovie;
    }

    @BeforeEach
    public void initTest() {
        seenMovie = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeenMovie() throws Exception {
        int databaseSizeBeforeCreate = seenMovieRepository.findAll().size();

        // Create the SeenMovie
        SeenMovieDTO seenMovieDTO = seenMovieMapper.toDto(seenMovie);
        restSeenMovieMockMvc.perform(post("/api/seen-movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seenMovieDTO)))
            .andExpect(status().isCreated());

        // Validate the SeenMovie in the database
        List<SeenMovie> seenMovieList = seenMovieRepository.findAll();
        assertThat(seenMovieList).hasSize(databaseSizeBeforeCreate + 1);
        SeenMovie testSeenMovie = seenMovieList.get(seenMovieList.size() - 1);
        assertThat(testSeenMovie.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createSeenMovieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seenMovieRepository.findAll().size();

        // Create the SeenMovie with an existing ID
        seenMovie.setId(1L);
        SeenMovieDTO seenMovieDTO = seenMovieMapper.toDto(seenMovie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeenMovieMockMvc.perform(post("/api/seen-movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seenMovieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SeenMovie in the database
        List<SeenMovie> seenMovieList = seenMovieRepository.findAll();
        assertThat(seenMovieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSeenMovies() throws Exception {
        // Initialize the database
        seenMovieRepository.saveAndFlush(seenMovie);

        // Get all the seenMovieList
        restSeenMovieMockMvc.perform(get("/api/seen-movies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seenMovie.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
    
    @Test
    @Transactional
    public void getSeenMovie() throws Exception {
        // Initialize the database
        seenMovieRepository.saveAndFlush(seenMovie);

        // Get the seenMovie
        restSeenMovieMockMvc.perform(get("/api/seen-movies/{id}", seenMovie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seenMovie.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingSeenMovie() throws Exception {
        // Get the seenMovie
        restSeenMovieMockMvc.perform(get("/api/seen-movies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeenMovie() throws Exception {
        // Initialize the database
        seenMovieRepository.saveAndFlush(seenMovie);

        int databaseSizeBeforeUpdate = seenMovieRepository.findAll().size();

        // Update the seenMovie
        SeenMovie updatedSeenMovie = seenMovieRepository.findById(seenMovie.getId()).get();
        // Disconnect from session so that the updates on updatedSeenMovie are not directly saved in db
        em.detach(updatedSeenMovie);
        updatedSeenMovie
            .date(UPDATED_DATE);
        SeenMovieDTO seenMovieDTO = seenMovieMapper.toDto(updatedSeenMovie);

        restSeenMovieMockMvc.perform(put("/api/seen-movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seenMovieDTO)))
            .andExpect(status().isOk());

        // Validate the SeenMovie in the database
        List<SeenMovie> seenMovieList = seenMovieRepository.findAll();
        assertThat(seenMovieList).hasSize(databaseSizeBeforeUpdate);
        SeenMovie testSeenMovie = seenMovieList.get(seenMovieList.size() - 1);
        assertThat(testSeenMovie.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSeenMovie() throws Exception {
        int databaseSizeBeforeUpdate = seenMovieRepository.findAll().size();

        // Create the SeenMovie
        SeenMovieDTO seenMovieDTO = seenMovieMapper.toDto(seenMovie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeenMovieMockMvc.perform(put("/api/seen-movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seenMovieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SeenMovie in the database
        List<SeenMovie> seenMovieList = seenMovieRepository.findAll();
        assertThat(seenMovieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeenMovie() throws Exception {
        // Initialize the database
        seenMovieRepository.saveAndFlush(seenMovie);

        int databaseSizeBeforeDelete = seenMovieRepository.findAll().size();

        // Delete the seenMovie
        restSeenMovieMockMvc.perform(delete("/api/seen-movies/{id}", seenMovie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeenMovie> seenMovieList = seenMovieRepository.findAll();
        assertThat(seenMovieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
