package fr.epita.ratingmovies.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.epita.ratingmovies.web.rest.TestUtil;

public class MovieDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieDTO.class);
        MovieDTO movieDTO1 = new MovieDTO();
        movieDTO1.setId(1L);
        MovieDTO movieDTO2 = new MovieDTO();
        assertThat(movieDTO1).isNotEqualTo(movieDTO2);
        movieDTO2.setId(movieDTO1.getId());
        assertThat(movieDTO1).isEqualTo(movieDTO2);
        movieDTO2.setId(2L);
        assertThat(movieDTO1).isNotEqualTo(movieDTO2);
        movieDTO1.setId(null);
        assertThat(movieDTO1).isNotEqualTo(movieDTO2);
    }
}
