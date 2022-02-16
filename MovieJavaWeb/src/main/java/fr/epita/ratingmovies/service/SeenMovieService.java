package fr.epita.ratingmovies.service;

import fr.epita.ratingmovies.domain.SeenMovie;
import fr.epita.ratingmovies.repository.SeenMovieRepository;
import fr.epita.ratingmovies.service.dto.SeenMovieDTO;
import fr.epita.ratingmovies.service.mapper.SeenMovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SeenMovie}.
 */
@Service
@Transactional
public class SeenMovieService {

    private final Logger log = LoggerFactory.getLogger(SeenMovieService.class);

    private final SeenMovieRepository seenMovieRepository;

    private final SeenMovieMapper seenMovieMapper;

    public SeenMovieService(SeenMovieRepository seenMovieRepository, SeenMovieMapper seenMovieMapper) {
        this.seenMovieRepository = seenMovieRepository;
        this.seenMovieMapper = seenMovieMapper;
    }

    /**
     * Save a seenMovie.
     *
     * @param seenMovieDTO the entity to save.
     * @return the persisted entity.
     */
    public SeenMovieDTO save(SeenMovieDTO seenMovieDTO) {
        log.debug("Request to save SeenMovie : {}", seenMovieDTO);
        SeenMovie seenMovie = seenMovieMapper.toEntity(seenMovieDTO);
        seenMovie = seenMovieRepository.save(seenMovie);
        return seenMovieMapper.toDto(seenMovie);
    }

    /**
     * Get all the seenMovies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SeenMovieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SeenMovies");
        return seenMovieRepository.findAll(pageable)
            .map(seenMovieMapper::toDto);
    }

    /**
     * Get one seenMovie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SeenMovieDTO> findOne(Long id) {
        log.debug("Request to get SeenMovie : {}", id);
        return seenMovieRepository.findById(id)
            .map(seenMovieMapper::toDto);
    }

    /**
     * Delete the seenMovie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SeenMovie : {}", id);
        seenMovieRepository.deleteById(id);
    }
}
