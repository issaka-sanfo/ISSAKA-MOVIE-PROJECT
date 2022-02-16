package fr.epita.ratingmovies.service;

import fr.epita.ratingmovies.domain.MovieUser;
import fr.epita.ratingmovies.repository.MovieUserRepository;
import fr.epita.ratingmovies.service.dto.MovieUserDTO;
import fr.epita.ratingmovies.service.mapper.MovieUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link MovieUser}.
 */
@Service
@Transactional
public class MovieUserService {

    private final Logger log = LoggerFactory.getLogger(MovieUserService.class);

    private final MovieUserRepository movieUserRepository;

    private final MovieUserMapper movieUserMapper;

    public MovieUserService(MovieUserRepository movieUserRepository, MovieUserMapper movieUserMapper) {
        this.movieUserRepository = movieUserRepository;
        this.movieUserMapper = movieUserMapper;
    }

    /**
     * Save a movieUser.
     *
     * @param movieUserDTO the entity to save.
     * @return the persisted entity.
     */
    public MovieUserDTO save(MovieUserDTO movieUserDTO) {
        log.debug("Request to save MovieUser : {}", movieUserDTO);
        MovieUser movieUser = movieUserMapper.toEntity(movieUserDTO);
        movieUser = movieUserRepository.save(movieUser);
        return movieUserMapper.toDto(movieUser);
    }

    /**
     * Get all the movieUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MovieUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovieUsers");
        return movieUserRepository.findAll(pageable)
            .map(movieUserMapper::toDto);
    }

    /**
     * Get all the movieUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MovieUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return movieUserRepository.findAllWithEagerRelationships(pageable).map(movieUserMapper::toDto);
    }


    /**
     *  Get all the movieUsers where Contact is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<MovieUserDTO> findAllWhereContactIsNull() {
        log.debug("Request to get all movieUsers where Contact is null");
        return StreamSupport
            .stream(movieUserRepository.findAll().spliterator(), false)
            .filter(movieUser -> movieUser.getContact() == null)
            .map(movieUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one movieUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieUserDTO> findOne(Long id) {
        log.debug("Request to get MovieUser : {}", id);
        return movieUserRepository.findOneWithEagerRelationships(id)
            .map(movieUserMapper::toDto);
    }

    /**
     * Delete the movieUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MovieUser : {}", id);
        movieUserRepository.deleteById(id);
    }
}
