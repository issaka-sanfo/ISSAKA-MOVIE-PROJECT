package fr.epita.ratingmovies.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "external_id")
    private String externalId;

    @OneToMany(mappedBy = "movie")
    private Set<SeenMovie> seenMovies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Movie title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Movie date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getExternalId() {
        return externalId;
    }

    public Movie externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Set<SeenMovie> getSeenMovies() {
        return seenMovies;
    }

    public Movie seenMovies(Set<SeenMovie> seenMovies) {
        this.seenMovies = seenMovies;
        return this;
    }

    public Movie addSeenMovie(SeenMovie seenMovie) {
        this.seenMovies.add(seenMovie);
        seenMovie.setMovie(this);
        return this;
    }

    public Movie removeSeenMovie(SeenMovie seenMovie) {
        this.seenMovies.remove(seenMovie);
        seenMovie.setMovie(null);
        return this;
    }

    public void setSeenMovies(Set<SeenMovie> seenMovies) {
        this.seenMovies = seenMovies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        return id != null && id.equals(((Movie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", externalId='" + getExternalId() + "'" +
            "}";
    }
}
