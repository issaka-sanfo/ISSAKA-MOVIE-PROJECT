package fr.epita.ratingmovies.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SeenMovieMapperTest {

    private SeenMovieMapper seenMovieMapper;

    @BeforeEach
    public void setUp() {
        seenMovieMapper = new SeenMovieMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(seenMovieMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(seenMovieMapper.fromId(null)).isNull();
    }
}
