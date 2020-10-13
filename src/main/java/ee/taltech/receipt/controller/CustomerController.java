package ee.taltech.receipt.controller;

import ee.taltech.receipt.dto.ReceiptSummary;
import ee.taltech.receipt.service.ReceiptService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("customer")
@RestController
@AllArgsConstructor
public class CustomerController {

    private final ReceiptService receiptService;

    @GetMapping("{id}/receipts")
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_OK,
            message = "Customer found"
        ),
        @ApiResponse(
            code = HttpServletResponse.SC_NOT_FOUND,
            message = "No customer exists for the given ID"
        ),
    })
    public ResponseEntity<?> getReceipts(@PathVariable Long id) {
        try {
            List<ReceiptSummary> receipts = receiptService.getAllCustomerReceipts(id)
                .stream()
                .map(ReceiptSummary::new)
                .collect(Collectors.toList());

            return new ResponseEntity<>(receipts, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}
