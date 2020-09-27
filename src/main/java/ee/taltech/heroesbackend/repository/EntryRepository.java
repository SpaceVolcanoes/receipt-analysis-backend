package ee.taltech.heroesbackend.repository;

import ee.taltech.heroesbackend.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

}
