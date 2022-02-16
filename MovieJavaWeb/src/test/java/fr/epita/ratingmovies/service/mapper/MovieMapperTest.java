package fr.epita.ratingmovies.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieMapperTest {

    private MovieMapper movieMapper;

    @BeforeEach
    public void setUp() {
        movieMapper = new MovieMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(movieMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movieMapper.fromId(null)).isNull();
    }
}
