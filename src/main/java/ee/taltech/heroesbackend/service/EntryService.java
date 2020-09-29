package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.model.Entry;
import ee.taltech.heroesbackend.repository.EntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
public class EntryService {

    private final EntryRepository repository;

    public Entry create(Entry entry) {
        if (entry.getId() != null) {
            throw new IllegalArgumentException("Attempting to re-create an existing Entry with ID " + entry.getId());
        }
        if (entry.getReceipt() == null) {
            throw new IllegalArgumentException("Attempting to create an Entry without a Receipt");
        }

        return repository.save(entry);
    }

    public Entry findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Entry with ID " + id));
    }

    public Entry update(Entry updated, Long id) {
        Entry old = findById(id);

        old.setCategory(updated.getCategory());
        old.setCost(updated.getCost());
        old.setName(updated.getName());
        old.setQuantity(updated.getQuantity());
        old.setReceipt(updated.getReceipt());
        old.setModifiedAt(Timestamp.from(Instant.now()));

        return repository.save(old);
    }

}
