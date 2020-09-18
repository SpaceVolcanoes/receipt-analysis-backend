package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.model.Hero;
import ee.taltech.heroesbackend.repository.HeroesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeroesServiceTest {

    @Mock
    private HeroesRepository repository;

    @InjectMocks
    private HeroesService service;

    @Test
    void findByIdAsksFromRepository() {
        Hero hero = mock(Hero.class);

        when(repository.findById(3L)).thenReturn(Optional.of(hero));

        Hero result = service.findById(3L);

        assertThat(result).isSameAs(hero);
    }

}
