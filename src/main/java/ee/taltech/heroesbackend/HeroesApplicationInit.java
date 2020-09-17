package ee.taltech.heroesbackend;

import ee.taltech.heroesbackend.model.Hero;
import ee.taltech.heroesbackend.repository.HeroesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeroesApplicationInit implements CommandLineRunner {

    @Autowired
    private HeroesRepository heroRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Hero> heroes = List.of(
                new Hero("Batman"),
                new Hero("Superman"),
                new Hero("Wonder Woman"),
                new Hero("Spider-Man"),
                new Hero("Wolverine"),
                new Hero("Thor"),
                new Hero("Black Widow"),
                new Hero("Captain Marvel"),
                new Hero("Wasp"),
                new Hero("Hulk")
        );
        heroRepository.saveAll(heroes);
    }
}
