package fr.epita.ratingmovies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.epita.ratingmovies.web.rest.TestUtil;

public class SeenMovieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeenMovie.class);
        SeenMovie seenMovie1 = new SeenMovie();
        seenMovie1.setId(1L);
        SeenMovie seenMovie2 = new SeenMovie();
        seenMovie2.setId(seenMovie1.getId());
        assertThat(seenMovie1).isEqualTo(seenMovie2);
        seenMovie2.setId(2L);
        assertThat(seenMovie1).isNotEqualTo(seenMovie2);
        seenMovie1.setId(null);
        assertThat(seenMovie1).isNotEqualTo(seenMovie2);
    }
}
