package fr.epita.ratingmovies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A SeenMovie.
 */
@Entity
@Table(name = "seen_movie")
public class SeenMovie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne
    @JsonIgnoreProperties("seenMovies")
    private Movie movie;

    @ManyToOne
    @JsonIgnoreProperties("seenMovies")
    private MovieUser movieUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public SeenMovie date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public SeenMovie movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieUser getMovieUser() {
        return movieUser;
    }

    public SeenMovie movieUser(MovieUser movieUser) {
        this.movieUser = movieUser;
        return this;
    }

    public void setMovieUser(MovieUser movieUser) {
        this.movieUser = movieUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeenMovie)) {
            return false;
        }
        return id != null && id.equals(((SeenMovie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SeenMovie{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
