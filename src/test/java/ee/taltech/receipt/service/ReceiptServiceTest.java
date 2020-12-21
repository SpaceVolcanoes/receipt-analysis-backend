package ee.taltech.receipt.service;

import ee.taltech.receipt.dto.Base64File;
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
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
    private CustomerService customerService;

    @Mock
    private EntryService entryService;

    @Mock
    private OcrService ocrService;

    @InjectMocks
    private ReceiptService service;

    @Test
    void getAmountReturnsCount() {
        when(repository.count()).thenReturn(10L);
        assertThat(service.getAmount()).isEqualTo(10L);
    }

    @Test
    void createThrowsIfFileNotImage() {
        when(fileService.isImage(FILE)).thenReturn(false);

        Throwable thrown = catchThrowable(() -> service.create(FILE, 1L));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Receipt file must be an image");
    }

    @Test
    void createReturnsReceipt() {
        when(fileService.isImage(FILE)).thenReturn(true);
        when(fileService.store(FILE)).thenReturn("receipt.png");
        when(repository.save(any(Receipt.class))).then(AdditionalAnswers.returnsFirstArg());

        Receipt receipt = service.create(FILE, 1L);

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
    void createAddsEntriesIfIdentified() {
        Base64File file = new Base64File();

        when(fileService.store(file)).thenReturn("temp.png");
        when(ocrService.identify("temp.png")).thenReturn(asList("first", "second"));
        when(repository.save(any(Receipt.class))).then(AdditionalAnswers.returnsFirstArg());

        service.create(file, 1L);

        ArgumentCaptor<Entry> captor = ArgumentCaptor.forClass(Entry.class);

        verify(entryService, times(2)).create(captor.capture());

        List<Entry> entries = captor.getAllValues();

        assertThat(entries.get(0).getName()).isEqualTo("first");
        assertThat(entries.get(0).getReceipt().getFileName()).isEqualTo("temp.png");

        assertThat(entries.get(1).getName()).isEqualTo("second");
        assertThat(entries.get(1).getReceipt().getFileName()).isEqualTo("temp.png");
    }

    @Test
    void getAllCustomerReceiptsDelegatesToCustomerService() {
        Receipt first = new Receipt();
        Receipt second = new Receipt();
        Customer given = new Customer().setReceipts(asList(first, second));

        when(customerService.findById(3L)).thenReturn(given);

        List<Receipt> actual = service.getAllCustomerReceipts(3L);

        assertThat(actual).containsExactly(first, second);
    }

}
