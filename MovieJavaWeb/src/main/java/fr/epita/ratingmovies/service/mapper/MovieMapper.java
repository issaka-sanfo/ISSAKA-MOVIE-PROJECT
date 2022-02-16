package fr.epita.ratingmovies.service.mapper;


import fr.epita.ratingmovies.domain.*;
import fr.epita.ratingmovies.service.dto.MovieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Movie} and its DTO {@link MovieDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {


    @Mapping(target = "seenMovies", ignore = true)
    @Mapping(target = "removeSeenMovie", ignore = true)
    Movie toEntity(MovieDTO movieDTO);

    default Movie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setId(id);
        return movie;
    }
}
