package ee.taltech.receipt.repository;

import ee.taltech.receipt.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    @Query("SELECT e FROM Entry e WHERE e.name = (SELECT a.name FROM Entry a where a.id = :id)")
    Collection<Entry> findSimilarTo(@Param("id") Long id);

}
