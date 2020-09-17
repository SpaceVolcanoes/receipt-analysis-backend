package ee.taltech.heroesbackend.repository;

import ee.taltech.heroesbackend.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroesRepository extends JpaRepository<Hero, Long> {

}
