package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.model.Receipt;
import ee.taltech.heroesbackend.repository.ReceiptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
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

}
