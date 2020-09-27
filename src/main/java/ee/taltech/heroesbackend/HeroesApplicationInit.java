package ee.taltech.heroesbackend;

import ee.taltech.heroesbackend.model.Customer;
import ee.taltech.heroesbackend.model.Entry;
import ee.taltech.heroesbackend.model.Hero;
import ee.taltech.heroesbackend.repository.EntryRepository;
import ee.taltech.heroesbackend.repository.HeroesRepository;
import ee.taltech.heroesbackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeroesApplicationInit implements CommandLineRunner {

    @Autowired
    private HeroesRepository heroRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntryRepository entryRepository;

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

        List<Customer> customers = List.of(
                new Customer()
                .setName("Krissu")
        );
        customerRepository.saveAll(customers);

        List<Entry> entries = List.of(
                new Entry()
                .setName("Mingi")
        );
        entryRepository.saveAll(entries);
    }
}
