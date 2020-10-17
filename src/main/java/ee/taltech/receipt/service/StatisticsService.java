package ee.taltech.receipt.service;

import ee.taltech.receipt.dto.GeneralStatistics;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final CustomerService customerService;
    private final ReceiptService receiptService;
    private final EntryService entryService;

    public GeneralStatistics generalStatistics() {
        Long customerAmount = customerService.getAmount();
        Long receiptAmount = receiptService.getAmount();
        Long entryAmount = entryService.getAmount();
        return new GeneralStatistics(customerAmount, receiptAmount, entryAmount);
    }

}
