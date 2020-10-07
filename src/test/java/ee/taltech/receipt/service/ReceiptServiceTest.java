package ee.taltech.receipt.service;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import ee.taltech.receipt.repository.ReceiptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    private static final MultipartFile FILE = mock(MultipartFile.class);

    @Mock
    private FileSystemStorageService fileService;

    @Mock
    private ReceiptRepository repository;

    @Mock
    private EntryService entryService;

    @InjectMocks
    private ReceiptService service;

    @Test
    void createThrowsIfFileNotImage() {
        when(fileService.isImage(FILE)).thenReturn(false);

        Throwable thrown = catchThrowable(() -> service.create(FILE));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Receipt file must be an image");
    }

    @Test
    void createReturnsReceipt() {
        when(fileService.isImage(FILE)).thenReturn(true);
        when(fileService.store(FILE)).thenReturn("receipt.png");
        when(repository.save(any(Receipt.class))).then(AdditionalAnswers.returnsFirstArg());

        Receipt receipt = service.create(FILE);

        assertThat(receipt.getFileName()).isEqualTo("receipt.png");
    }

    @Test
    void findByIdReturnsReceiptFromRepository() {
        Receipt receipt = new Receipt();

        when(repository.findById(3L)).thenReturn(Optional.of(receipt));

        Receipt actual = service.findById(3L);

        assertThat(actual).isSameAs(receipt);
        verify(repository).findById(3L);
    }

    @Test
    void findByIdThrowsWhenNotFound() {
        when(repository.findById(3L)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> service.findById(3L));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("No Receipt with ID 3");

        verify(repository).findById(3L);
    }

    @Test
    void updateSetsNewValuesOnReceipt() {
        Receipt old = new Receipt().setId(1L)
            .setFileName("temp.png")
            .setCustomer(new Customer().setId(5L))
            .setIssuer("Maxima")
            .setModifiedAt(Timestamp.valueOf("2020-09-13 11:00:00"))
            .setCreatedAt(Timestamp.valueOf("2020-09-13 11:00:00"))
            .setIssuedAt(Timestamp.valueOf("2020-09-13 11:00:00"));

        when(repository.findById(1L)).thenReturn(Optional.of(old));
        when(repository.save(any(Receipt.class))).then(AdditionalAnswers.returnsFirstArg());

        Receipt updated = new Receipt().setId(3L)
            .setFileName("moo.png")
            .setCustomer(new Customer().setId(8L))
            .setIssuer("Krauta")
            .setEntries(emptyList())
            .setModifiedAt(Timestamp.valueOf("2020-09-15 11:00:00"))
            .setCreatedAt(Timestamp.valueOf("2020-09-15 11:00:00"))
            .setIssuedAt(Timestamp.valueOf("2020-09-15 11:00:00"));

        Receipt actual = service.update(updated, 1L);

        assertThat(actual).isSameAs(old);
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getFileName()).isEqualTo("temp.png");
        assertThat(actual.getCustomer().getId()).isEqualTo(5L);
        assertThat(actual.getIssuer()).isEqualTo("Krauta");
        assertThat(actual.getCreatedAt()).isEqualTo(Timestamp.valueOf("2020-09-13 11:00:00"));
        assertThat(actual.getIssuedAt()).isEqualTo(Timestamp.valueOf("2020-09-15 11:00:00"));
        assertThat(actual.getModifiedAt()).isAfter(Date.from(Instant.parse("2020-09-19T11:00:00.000Z")));
    }

    @Test
    void updateAddsAndUpdatesChildEntries() {
        Receipt old = new Receipt();
        Receipt updated = new Receipt();

        Entry existingEntry = new Entry().setId(3L).setReceipt(updated);
        Entry newEntry = new Entry().setReceipt(updated);

        updated.setEntries(asList(existingEntry, newEntry));

        ArgumentCaptor<Entry> captor = ArgumentCaptor.forClass(Entry.class);

        when(repository.findById(1L)).thenReturn(Optional.of(old));
        service.update(updated, 1L);

        verify(entryService).create(captor.capture());
        verify(entryService).update(captor.capture(), eq(3L));

        assertThat(captor.getAllValues()).containsExactlyInAnyOrder(existingEntry, newEntry);
        assertThat(captor.getValue().getReceipt()).isSameAs(old);
    }

}
