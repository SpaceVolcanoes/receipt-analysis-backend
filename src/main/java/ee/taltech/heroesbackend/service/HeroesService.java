package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.exception.HeroNotFoundException;
import ee.taltech.heroesbackend.exception.InvalidHeroException;
import ee.taltech.heroesbackend.model.Hero;
import ee.taltech.heroesbackend.repository.HeroesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeroesService {

    private HeroesRepository heroesRepository;

    public HeroesService(HeroesRepository heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public List<Hero> findAll(String name) {
        //todo add name
        return heroesRepository.findAll();
    }

    public Hero findById(Long id) {
        return heroesRepository.findById(id)
                .orElseThrow(HeroNotFoundException::new);
    }

    public Hero save(Hero hero) {
        if (hero.getName() == null) {
            throw new InvalidHeroException("Hero has no name");
        }
        if (hero.getId() != null){
            throw new InvalidHeroException("Id is already present");
        }
        // save will generate id for object
        return heroesRepository.save(hero);
    }

    public Hero update(Hero hero, Long id) {
        if (hero.getName() == null) {
            throw new InvalidHeroException("Hero has no name");
        }
        Hero dbHero = findById(id);
        dbHero.setName(hero.getName());
        // save works as update when id is present
        return heroesRepository.save(dbHero);
    }

    public void delete(Long id) {
        Hero dbHero = findById(id);
        heroesRepository.delete(dbHero);
    }
}
