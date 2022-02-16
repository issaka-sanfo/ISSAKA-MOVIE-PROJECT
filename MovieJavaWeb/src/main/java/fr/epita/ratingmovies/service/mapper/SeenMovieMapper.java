package fr.epita.ratingmovies.service.mapper;


import fr.epita.ratingmovies.domain.*;
import fr.epita.ratingmovies.service.dto.SeenMovieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SeenMovie} and its DTO {@link SeenMovieDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieMapper.class, MovieUserMapper.class})
public interface SeenMovieMapper extends EntityMapper<SeenMovieDTO, SeenMovie> {

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movieUser.id", target = "movieUserId")
    SeenMovieDTO toDto(SeenMovie seenMovie);

    @Mapping(source = "movieId", target = "movie")
    @Mapping(source = "movieUserId", target = "movieUser")
    SeenMovie toEntity(SeenMovieDTO seenMovieDTO);

    default SeenMovie fromId(Long id) {
        if (id == null) {
            return null;
        }
        SeenMovie seenMovie = new SeenMovie();
        seenMovie.setId(id);
        return seenMovie;
    }
}
