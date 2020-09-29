package ee.taltech.heroesbackend.controller;

import ee.taltech.heroesbackend.exception.StorageException;
import ee.taltech.heroesbackend.model.Receipt;
import ee.taltech.heroesbackend.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptControllerTest {

    private static final MultipartFile FILE = mock(MultipartFile.class);

    @Mock
    private ReceiptService service;

    @InjectMocks
    private ReceiptController controller;

    @Test
    void createReturnsIdFromService() {
        when(service.create(FILE)).thenReturn(new Receipt().setId(3L));

        ResponseEntity<?> response = controller.create(FILE);

        assertThat(response.getBody()).isEqualTo(3L);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void createReturnsRetryIfStoringFailed() {
        when(service.create(FILE)).thenThrow(StorageException.class);

        ResponseEntity<?> response = controller.create(FILE);

        assertThat(response.getStatusCodeValue()).isEqualTo(503);
        assertThat(response.getHeaders()).containsEntry("Retry-After", singletonList("10"));
    }

    @Test
    void createReturnsUnsupportedIfWrongFileType() {
        when(service.create(FILE)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.create(FILE);

        assertThat(response.getStatusCodeValue()).isEqualTo(415);
    }

    @Test
    void getReceiptReturnsReceiptFromServiceIfFound() {
        Receipt receipt = new Receipt();

        when(service.findById(3L)).thenReturn(receipt);

        ResponseEntity<?> response = controller.getReceipt(3L);

        assertThat(response.getBody()).isSameAs(receipt);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getReceiptReturnsNotFoundIfServiceThrowsIllegal() {
        when(service.findById(3L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.getReceipt(3L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

}
