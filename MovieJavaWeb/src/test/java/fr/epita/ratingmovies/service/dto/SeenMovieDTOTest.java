package fr.epita.ratingmovies.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.epita.ratingmovies.web.rest.TestUtil;

public class SeenMovieDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeenMovieDTO.class);
        SeenMovieDTO seenMovieDTO1 = new SeenMovieDTO();
        seenMovieDTO1.setId(1L);
        SeenMovieDTO seenMovieDTO2 = new SeenMovieDTO();
        assertThat(seenMovieDTO1).isNotEqualTo(seenMovieDTO2);
        seenMovieDTO2.setId(seenMovieDTO1.getId());
        assertThat(seenMovieDTO1).isEqualTo(seenMovieDTO2);
        seenMovieDTO2.setId(2L);
        assertThat(seenMovieDTO1).isNotEqualTo(seenMovieDTO2);
        seenMovieDTO1.setId(null);
        assertThat(seenMovieDTO1).isNotEqualTo(seenMovieDTO2);
    }
}
