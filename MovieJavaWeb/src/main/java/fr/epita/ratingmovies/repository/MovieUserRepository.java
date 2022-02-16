package fr.epita.ratingmovies.repository;

import fr.epita.ratingmovies.domain.MovieUser;

import fr.epita.ratingmovies.service.dto.MovieUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MovieUser entity.
 */
@Repository
public interface MovieUserRepository extends JpaRepository<MovieUser, Long> {

    @Query(value = "select distinct movieUser from MovieUser movieUser left join fetch movieUser.roles",
        countQuery = "select count(distinct movieUser) from MovieUser movieUser")
    Page<MovieUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct movieUser from MovieUser movieUser left join fetch movieUser.roles")
    List<MovieUser> findAllWithEagerRelationships();

    @Query("select movieUser from MovieUser movieUser left join fetch movieUser.roles where movieUser.id =:id")
    Optional<MovieUser> findOneWithEagerRelationships(@Param("id") Long id);

    MovieUser findByUsername(String login);
}
