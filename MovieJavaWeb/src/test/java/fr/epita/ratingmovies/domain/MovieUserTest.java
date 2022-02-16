package fr.epita.ratingmovies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.epita.ratingmovies.web.rest.TestUtil;

public class MovieUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieUser.class);
        MovieUser movieUser1 = new MovieUser();
        movieUser1.setId(1L);
        MovieUser movieUser2 = new MovieUser();
        movieUser2.setId(movieUser1.getId());
        assertThat(movieUser1).isEqualTo(movieUser2);
        movieUser2.setId(2L);
        assertThat(movieUser1).isNotEqualTo(movieUser2);
        movieUser1.setId(null);
        assertThat(movieUser1).isNotEqualTo(movieUser2);
    }
}
