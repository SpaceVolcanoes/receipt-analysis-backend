package ee.taltech.receipt.controller;

import ee.taltech.receipt.exception.StorageException;
import ee.taltech.receipt.model.Receipt;
import ee.taltech.receipt.security.UserSessionService;
import ee.taltech.receipt.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptControllerTest {

    private static final MultipartFile FILE = mock(MultipartFile.class);

    @Mock
    private ReceiptService receiptService;

    @Mock
    private Logger logger;

    @Mock
    private UserSessionService sessionService;

    @InjectMocks
    private ReceiptController controller;

    @Test
    void createReturnsIdFromService() {
        when(receiptService.create(FILE, 1L)).thenReturn(new Receipt().setId(3L));

        ResponseEntity<?> response = controller.create(FILE);

        assertThat(response.getBody()).isEqualTo(3L);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void createReturnsRetryIfStoringFailed() {
        when(receiptService.create(FILE, 1L)).thenThrow(StorageException.class);

        ResponseEntity<?> response = controller.create(FILE);

        assertThat(response.getStatusCodeValue()).isEqualTo(503);
        assertThat(response.getHeaders()).containsEntry("Retry-After", singletonList("10"));
        verify(logger).error(any(), any(StorageException.class));
    }

    @Test
    void createReturnsUnsupportedIfWrongFileType() {
        when(receiptService.create(FILE, 1L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.create(FILE);

        assertThat(response.getStatusCodeValue()).isEqualTo(415);
        verify(logger).warn(any(), any(IllegalArgumentException.class));
    }

    @Test
    void createReturnsForbiddenIfIllegalState() {
        when(receiptService.create(FILE, 1L)).thenThrow(IllegalStateException.class);

        ResponseEntity<?> response = controller.create(FILE);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
        verify(logger).warn(any(), any(IllegalStateException.class));
    }

    @Test
    void getReceiptReturnsReceiptFromServiceIfFound() {
        Receipt receipt = new Receipt();

        when(receiptService.findById(3L)).thenReturn(receipt);

        ResponseEntity<?> response = controller.getReceipt(3L);

        assertThat(response.getBody()).isSameAs(receipt);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getReceiptReturnsNotFoundIfServiceThrowsIllegal() {
        when(receiptService.findById(3L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.getReceipt(3L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void updateReceiptReturnsReceiptIfSuccess() {
        Receipt receipt = new Receipt();

        when(receiptService.update(receipt, 3L)).thenReturn(receipt);

        ResponseEntity<?> response = controller.updateReceipt(receipt, 3L);

        assertThat(response.getBody()).isSameAs(receipt);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void updateReceiptReturnsBadRequestIfError() {
        Receipt receipt = new Receipt();

        when(receiptService.update(receipt, 3L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.updateReceipt(receipt, 3L);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void deleteReceiptReturnsOkIfSuccess() {
        Receipt receipt = new Receipt();

        when(receiptService.findById(3L)).thenReturn(receipt);

        ResponseEntity<?> responseOk = controller.delete(3L);

        assertThat(responseOk.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void deleteReceiptReturnsNotFoundIfServiceThrowsIllegal() {
        when(receiptService.findById(3L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.delete(3L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

}
