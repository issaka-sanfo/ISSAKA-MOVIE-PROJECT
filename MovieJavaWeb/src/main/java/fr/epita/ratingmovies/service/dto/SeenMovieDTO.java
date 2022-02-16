package fr.epita.ratingmovies.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.epita.ratingmovies.domain.SeenMovie} entity.
 */
public class SeenMovieDTO implements Serializable {
    
    private Long id;

    private ZonedDateTime date;


    private Long movieId;

    private Long movieUserId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getMovieUserId() {
        return movieUserId;
    }

    public void setMovieUserId(Long movieUserId) {
        this.movieUserId = movieUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeenMovieDTO seenMovieDTO = (SeenMovieDTO) o;
        if (seenMovieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seenMovieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeenMovieDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", movieId=" + getMovieId() +
            ", movieUserId=" + getMovieUserId() +
            "}";
    }
}
