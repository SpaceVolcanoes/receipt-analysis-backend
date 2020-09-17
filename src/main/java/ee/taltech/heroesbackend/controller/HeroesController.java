package ee.taltech.heroesbackend.controller;

import ee.taltech.heroesbackend.model.Hero;
import ee.taltech.heroesbackend.service.HeroesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("heroes")
@RestController
public class HeroesController {

    @Autowired
    private HeroesService heroesService;

    @GetMapping
    public List<Hero> getHeroes(@RequestParam(value = "name", required = false) String name) {
        return heroesService.findAll(name);
    }

    //todo get 1
    @GetMapping("{id}")
    public Hero getHero(@PathVariable Long id) {
        return heroesService.findById(id);
    }

    //todo save
    @PostMapping
    public Hero saveHero(@RequestBody Hero hero) {
        return heroesService.save(hero);
    }

    //todo update
    @PutMapping("{id}")
    public Hero updateHero(@RequestBody Hero hero, @PathVariable Long id) {
        return heroesService.update(hero, id);
    }

    //todo delete
    @DeleteMapping("{id}")
    public void updateHero(@PathVariable Long id) {
        heroesService.delete(id);
    }
    //todo add search by name
    //todo validation?
    //todo add tests
}
