package ee.taltech.receipt.service;

import ee.taltech.receipt.dto.GeneralStatistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private ReceiptService receiptService;

    @Mock
    private EntryService entryService;

    @InjectMocks
    private StatisticsService service;

    @Test
    void generalStatisticsReturnsCorrect() {
        when(customerService.getAmount()).thenReturn(3L);
        when(receiptService.getAmount()).thenReturn(5L);
        when(entryService.getAmount()).thenReturn(10L);

        GeneralStatistics statistics = service.generalStatistics();

        assertThat(statistics.getCustomerAmount()).isEqualTo(3L);
        assertThat(statistics.getReceiptAmount()).isEqualTo(5L);
        assertThat(statistics.getEntryAmount()).isEqualTo(10L);
        assertThat(statistics.getEntriesPerReceipt()).isEqualTo(2.0);
    }

}
