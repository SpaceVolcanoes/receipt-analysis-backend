package ee.taltech.heroesbackend.controller;

import ee.taltech.heroesbackend.exception.HeroNotFoundException;
import ee.taltech.heroesbackend.exception.InvalidHeroException;
import ee.taltech.heroesbackend.model.Hero;
import ee.taltech.heroesbackend.repository.HeroesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This was a class we used to quickly show API.
 * In real life we never have state or logic inside a controller
 * For state we have database and for logic we have service.
 *
 * This class is a singleton what is requested by multiple different clients (different browser or machine requests)
 * If we have state here it will get mixed up between clients.
 */
@Deprecated
@RequestMapping("v1/heroes")
@RestController
public class HeroesV1Controller {

    //never use state on controllers, however for example this is ok
    private List<Hero> heroes = new ArrayList<>();

    public HeroesV1Controller() {
        this.heroes.add(new Hero(1L, "Superman"));
        this.heroes.add(new Hero(2L, "Batman"));
    }

    @GetMapping
    public List<Hero> getHeroes(@RequestParam(value = "name", required = false) String name) {
        //filter to try to match names
        return heroes;
    }

    //todo get 1
    @GetMapping("{id}")
    public Hero getHero(@PathVariable Long id) {
        return heroes.stream()
                .filter(hero -> hero.getId().equals(id))
                .findAny().orElseThrow(HeroNotFoundException::new);
    }

    //todo save
    @PostMapping
    public Hero saveHero(@RequestBody Hero hero) {
        if (hero.getName() == null) {
            throw new InvalidHeroException("Hero has no name");
        }
        heroes.add(hero);
        return hero;
    }

    //todo update
    @PutMapping("{id}")
    public Hero updateHero(@RequestBody Hero hero, @PathVariable Long id) {
        for (Hero hero1 : heroes) {
            if (hero1.getId().equals(id)) {
                hero1.setName(hero.getName());
            }
        }
//        Hero dbHero = getHero(id);
//        dbHero.setName(hero.getName());
        return hero;
    }

    //todo delete
    @DeleteMapping("{id}")
    public void updateHero(@PathVariable Long id) {
        // we just set heroes to be heroes without the id
        heroes = heroes.stream()
                .filter(h -> !h.getId().equals(id))
                .collect(Collectors.toList());
    }
    //todo add search by name
    //todo validation?
    //todo add tests
}
