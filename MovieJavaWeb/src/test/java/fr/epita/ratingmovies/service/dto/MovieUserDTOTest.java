package fr.epita.ratingmovies.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.epita.ratingmovies.web.rest.TestUtil;

public class MovieUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieUserDTO.class);
        MovieUserDTO movieUserDTO1 = new MovieUserDTO();
        movieUserDTO1.setId(1L);
        MovieUserDTO movieUserDTO2 = new MovieUserDTO();
        assertThat(movieUserDTO1).isNotEqualTo(movieUserDTO2);
        movieUserDTO2.setId(movieUserDTO1.getId());
        assertThat(movieUserDTO1).isEqualTo(movieUserDTO2);
        movieUserDTO2.setId(2L);
        assertThat(movieUserDTO1).isNotEqualTo(movieUserDTO2);
        movieUserDTO1.setId(null);
        assertThat(movieUserDTO1).isNotEqualTo(movieUserDTO2);
    }
}
