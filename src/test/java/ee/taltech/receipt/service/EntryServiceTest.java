package ee.taltech.receipt.service;

import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import ee.taltech.receipt.repository.EntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntryServiceTest {

    @Mock
    private EntryRepository repository;

    @InjectMocks
    private EntryService service;

    @Test
    void createThrowsIfIdPresent() {
        Throwable thrown = catchThrowable(() -> service.create(new Entry().setId(3L)));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Attempting to re-create an existing Entry with ID 3");
    }

    @Test
    void createThrowsIfMissingReceipt() {
        Throwable thrown = catchThrowable(() -> service.create(new Entry()));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Attempting to create an Entry without a Receipt");
    }

    @Test
    void createReturnsEntry() {
        Entry entry = new Entry().setReceipt(new Receipt());

        when(repository.save(entry)).thenReturn(entry);

        Entry actual = service.create(entry);

        assertThat(actual).isSameAs(entry);
    }

    @Test
    void findByIdReturnsEntryFromRepository() {
        Entry entry = new Entry();

        when(repository.findById(3L)).thenReturn(Optional.of(entry));

        Entry actual = service.findById(3L);

        assertThat(actual).isSameAs(entry);
        verify(repository).findById(3L);
    }

    @Test
    void findByIdThrowsWhenNotFound() {
        when(repository.findById(3L)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> service.findById(3L));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("No Entry with ID 3");

        verify(repository).findById(3L);
    }

    @Test
    void updateSetsNewValues() {
        Receipt receipt = new Receipt();

        Entry old = new Entry().setId(1L)
            .setCategory("Food")
            .setCost(3.5)
            .setName("Milk")
            .setQuantity(5L)
            .setReceipt(null)
            .setCreatedAt(Timestamp.valueOf("2020-09-14 11:00:00"))
            .setModifiedAt(Timestamp.valueOf("2020-09-14 11:00:00"));

        when(repository.findById(1L)).thenReturn(Optional.of(old));
        when(repository.save(any(Entry.class))).then(AdditionalAnswers.returnsFirstArg());

        Entry updated = new Entry().setId(1L)
            .setCategory("Car parts")
            .setCost(11.0)
            .setName("Audi")
            .setQuantity(7L)
            .setReceipt(receipt)
            .setCreatedAt(Timestamp.valueOf("2020-09-19 11:00:00"))
            .setModifiedAt(Timestamp.valueOf("2020-09-19 11:00:00"));

        Entry actual = service.update(updated, 1L);

        assertThat(actual).isSameAs(old);
        assertThat(actual.getCategory()).isEqualTo("Car parts");
        assertThat(actual.getCost()).isEqualTo(11.0);
        assertThat(actual.getName()).isEqualTo("Audi");
        assertThat(actual.getQuantity()).isEqualTo(7L);
        assertThat(actual.getReceipt()).isSameAs(receipt);
        assertThat(actual.getCreatedAt()).isEqualTo(Timestamp.valueOf("2020-09-14 11:00:00"));
        assertThat(actual.getModifiedAt()).isAfter(Date.from(Instant.parse("2020-09-21T11:00:00.000Z")));
    }

}
