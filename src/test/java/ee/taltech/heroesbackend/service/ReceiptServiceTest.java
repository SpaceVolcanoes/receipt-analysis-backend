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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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

}
