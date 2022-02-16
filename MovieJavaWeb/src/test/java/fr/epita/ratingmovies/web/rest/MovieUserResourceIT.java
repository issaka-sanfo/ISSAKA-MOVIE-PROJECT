package fr.epita.ratingmovies.web.rest;

import fr.epita.ratingmovies.MovieJavaWebApp;
import fr.epita.ratingmovies.domain.MovieUser;
import fr.epita.ratingmovies.repository.MovieUserRepository;
import fr.epita.ratingmovies.service.MovieUserService;
import fr.epita.ratingmovies.service.dto.MovieUserDTO;
import fr.epita.ratingmovies.service.mapper.MovieUserMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MovieUserResource} REST controller.
 */
@SpringBootTest(classes = MovieJavaWebApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MovieUserResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    @Autowired
    private MovieUserRepository movieUserRepository;

    @Mock
    private MovieUserRepository movieUserRepositoryMock;

    @Autowired
    private MovieUserMapper movieUserMapper;

    @Mock
    private MovieUserService movieUserServiceMock;

    @Autowired
    private MovieUserService movieUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieUserMockMvc;

    private MovieUser movieUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieUser createEntity(EntityManager em) {
        MovieUser movieUser = new MovieUser()
            .username(DEFAULT_USERNAME);
        return movieUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieUser createUpdatedEntity(EntityManager em) {
        MovieUser movieUser = new MovieUser()
            .username(UPDATED_USERNAME);
        return movieUser;
    }

    @BeforeEach
    public void initTest() {
        movieUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieUser() throws Exception {
        int databaseSizeBeforeCreate = movieUserRepository.findAll().size();

        // Create the MovieUser
        MovieUserDTO movieUserDTO = movieUserMapper.toDto(movieUser);
        restMovieUserMockMvc.perform(post("/api/movie-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieUserDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieUser in the database
        List<MovieUser> movieUserList = movieUserRepository.findAll();
        assertThat(movieUserList).hasSize(databaseSizeBeforeCreate + 1);
        MovieUser testMovieUser = movieUserList.get(movieUserList.size() - 1);
        assertThat(testMovieUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
    }

    @Test
    @Transactional
    public void createMovieUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieUserRepository.findAll().size();

        // Create the MovieUser with an existing ID
        movieUser.setId(1L);
        MovieUserDTO movieUserDTO = movieUserMapper.toDto(movieUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieUserMockMvc.perform(post("/api/movie-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieUser in the database
        List<MovieUser> movieUserList = movieUserRepository.findAll();
        assertThat(movieUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMovieUsers() throws Exception {
        // Initialize the database
        movieUserRepository.saveAndFlush(movieUser);

        // Get all the movieUserList
        restMovieUserMockMvc.perform(get("/api/movie-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMovieUsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(movieUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovieUserMockMvc.perform(get("/api/movie-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(movieUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMovieUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(movieUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovieUserMockMvc.perform(get("/api/movie-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(movieUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMovieUser() throws Exception {
        // Initialize the database
        movieUserRepository.saveAndFlush(movieUser);

        // Get the movieUser
        restMovieUserMockMvc.perform(get("/api/movie-users/{id}", movieUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movieUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME));
    }

    @Test
    @Transactional
    public void getNonExistingMovieUser() throws Exception {
        // Get the movieUser
        restMovieUserMockMvc.perform(get("/api/movie-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieUser() throws Exception {
        // Initialize the database
        movieUserRepository.saveAndFlush(movieUser);

        int databaseSizeBeforeUpdate = movieUserRepository.findAll().size();

        // Update the movieUser
        MovieUser updatedMovieUser = movieUserRepository.findById(movieUser.getId()).get();
        // Disconnect from session so that the updates on updatedMovieUser are not directly saved in db
        em.detach(updatedMovieUser);
        updatedMovieUser
            .username(UPDATED_USERNAME);
        MovieUserDTO movieUserDTO = movieUserMapper.toDto(updatedMovieUser);

        restMovieUserMockMvc.perform(put("/api/movie-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieUserDTO)))
            .andExpect(status().isOk());

        // Validate the MovieUser in the database
        List<MovieUser> movieUserList = movieUserRepository.findAll();
        assertThat(movieUserList).hasSize(databaseSizeBeforeUpdate);
        MovieUser testMovieUser = movieUserList.get(movieUserList.size() - 1);
        assertThat(testMovieUser.getUsername()).isEqualTo(UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieUser() throws Exception {
        int databaseSizeBeforeUpdate = movieUserRepository.findAll().size();

        // Create the MovieUser
        MovieUserDTO movieUserDTO = movieUserMapper.toDto(movieUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieUserMockMvc.perform(put("/api/movie-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movieUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovieUser in the database
        List<MovieUser> movieUserList = movieUserRepository.findAll();
        assertThat(movieUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovieUser() throws Exception {
        // Initialize the database
        movieUserRepository.saveAndFlush(movieUser);

        int databaseSizeBeforeDelete = movieUserRepository.findAll().size();

        // Delete the movieUser
        restMovieUserMockMvc.perform(delete("/api/movie-users/{id}", movieUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MovieUser> movieUserList = movieUserRepository.findAll();
        assertThat(movieUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
