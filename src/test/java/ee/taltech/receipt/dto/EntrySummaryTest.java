package ee.taltech.receipt.dto;

import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EntrySummaryTest {

    private Entry entry;

    private EntrySummary entrySummary;

    @BeforeEach
    void setUp() {
        Receipt receipt = new Receipt().setId(2L);
        entry = new Entry()
            .setId(1L)
            .setReceipt(receipt)
            .setName("Muna")
            .setCategory("Toit")
            .setQuantity(2L)
            .setCost(5.0);
        entrySummary = new EntrySummary(entry);
    }

    @Test
    void testSummary() {
        assertThat(entrySummary.getId()).isEqualTo(1L);
        assertThat(entrySummary.getReceiptId()).isEqualTo(2L);
        assertThat(entrySummary.getName()).isEqualTo("Muna");
        assertThat(entrySummary.getCategory()).isEqualTo("Toit");
        assertThat(entrySummary.getQuantity()).isEqualTo(2L);
        assertThat(entrySummary.getCost()).isEqualTo(5.0);
    }

}
