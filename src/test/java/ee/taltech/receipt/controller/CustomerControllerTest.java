package ee.taltech.receipt.controller;

import ee.taltech.receipt.dto.ReceiptSummary;
import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.model.Receipt;
import ee.taltech.receipt.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private ReceiptService service;

    @InjectMocks
    private CustomerController controller;

    @Test
    void getReceiptsReturnsReceiptsFromServiceIfFound() {
        Receipt receipt = new Receipt()
            .setId(5L)
            .setCustomer(new Customer())
            .setEntries(List.of());
        List<Receipt> receipts = List.of(receipt);

        when(service.getAllCustomerReceipts(3L)).thenReturn(receipts);

        ResponseEntity<?> response = controller.getReceipts(3L);

        List<ReceiptSummary> body = (List) response.getBody();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(body.get(0).getId()).isEqualTo(5L);
    }

    @Test
    void getReceiptsReturnsNotFoundIfServiceThrowsIllegal() {
        when(service.getAllCustomerReceipts(3L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.getReceipts(3L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}