package fr.epita.ratingmovies.service.mapper;


import fr.epita.ratingmovies.domain.*;
import fr.epita.ratingmovies.service.dto.MovieUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MovieUser} and its DTO {@link MovieUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class, UserMapper.class})
public interface MovieUserMapper extends EntityMapper<MovieUserDTO, MovieUser> {

    /*@Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    MovieUserDTO toDto(MovieUser movieUser);

    @Mapping(target = "seenMovies", ignore = true)
    @Mapping(target = "removeSeenMovie", ignore = true)
    @Mapping(target = "removeRole", ignore = true)
    @Mapping(target = "contact", ignore = true)
    @Mapping(source = "userId", target = "user")
    MovieUser toEntity(MovieUserDTO movieUserDTO);*/

    default MovieUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieUser movieUser = new MovieUser();
        movieUser.setId(id);
        return movieUser;
    }
}
