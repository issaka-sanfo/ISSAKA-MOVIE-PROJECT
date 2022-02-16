package fr.epita.ratingmovies.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieUserMapperTest {

    private MovieUserMapper movieUserMapper;

    @BeforeEach
    public void setUp() {
        movieUserMapper = new MovieUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(movieUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movieUserMapper.fromId(null)).isNull();
    }
}
