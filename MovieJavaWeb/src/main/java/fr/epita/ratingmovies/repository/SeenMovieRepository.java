package fr.epita.ratingmovies.repository;

import fr.epita.ratingmovies.domain.MovieUser;
import fr.epita.ratingmovies.domain.SeenMovie;

import fr.epita.ratingmovies.service.dto.SeenMovieDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the SeenMovie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeenMovieRepository extends JpaRepository<SeenMovie, Long> {

    SeenMovie findFirstByMovieUserIdOrderByDateDesc(Long id);

}
