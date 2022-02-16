package fr.epita.ratingmovies.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A MovieUser.
 */
@Entity
@Table(name = "movie_user")
public class MovieUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "username")
    private String username;


    @OneToMany(mappedBy = "movieUser")
    private Set<SeenMovie> seenMovies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "movie_user_role",
               joinColumns = @JoinColumn(name = "movie_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "movieUser")
    @JsonIgnore
    private Contact contact;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public MovieUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<SeenMovie> getSeenMovies() {
        return seenMovies;
    }

    public MovieUser seenMovies(Set<SeenMovie> seenMovies) {
        this.seenMovies = seenMovies;
        return this;
    }

    public MovieUser addSeenMovie(SeenMovie seenMovie) {
        this.seenMovies.add(seenMovie);
        seenMovie.setMovieUser(this);
        return this;
    }

    public MovieUser removeSeenMovie(SeenMovie seenMovie) {
        this.seenMovies.remove(seenMovie);
        seenMovie.setMovieUser(null);
        return this;
    }

    public void setSeenMovies(Set<SeenMovie> seenMovies) {
        this.seenMovies = seenMovies;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public MovieUser roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public MovieUser addRole(Role role) {
        this.roles.add(role);
        role.getMovieUsers().add(this);
        return this;
    }

    public MovieUser removeRole(Role role) {
        this.roles.remove(role);
        role.getMovieUsers().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Contact getContact() {
        return contact;
    }

    public MovieUser contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieUser)) {
            return false;
        }
        return id != null && id.equals(((MovieUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MovieUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            "}";
    }
}
