package ee.taltech.receipt.dto;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

class ReceiptSummaryTest {

    private Receipt receipt1;
    private Receipt receipt2;

    private ReceiptSummary receiptSummary1;
    private ReceiptSummary receiptSummary2;

    @BeforeEach
    void setUp() {
        List<Entry> entries1 = List.of(
            new Entry().setName("Piim").setCost(2.0).setReceipt(receipt1),
            new Entry().setName("Juust").setCost(3.0).setReceipt(receipt1),
            new Entry().setName("Rõba").setCost(4.0).setReceipt(receipt1),
            new Entry().setName("Köis").setCost(3.0).setReceipt(receipt1),
            new Entry().setName("Taburet").setCost(15.99).setReceipt(receipt1)
        );

        List<Entry> entries2 = List.of(
            new Entry().setName("Muna").setCost(2.99).setReceipt(receipt2),
            new Entry().setName("Vesi").setCost(0.99).setReceipt(receipt2),
            new Entry().setName("Kana").setCost(3.35).setReceipt(receipt2)
        );

	    receipt1 = new Receipt().setCustomer(new Customer()).setEntries(entries1);
        receipt2 = new Receipt().setCustomer(new Customer()).setEntries(entries2);

        receiptSummary1 = new ReceiptSummary(receipt1);
        receiptSummary2 = new ReceiptSummary(receipt2);
    }

    @Test
    void testNumberOfEntries() {
        assertThat(receiptSummary1.getNumberOfEntries()).isEqualTo(5);
        assertThat(receiptSummary2.getNumberOfEntries()).isEqualTo(3);
    }

    @Test
    void testTotalCostOfEntries() {
        double totalCost1 = receiptSummary1.getTotalCostOfEntries();
        assertThat(totalCost1).isEqualTo(27.99, offset(0.001));

        double totalCost2 = receiptSummary2.getTotalCostOfEntries();
        assertThat(totalCost2).isEqualTo(7.33, offset(0.001));
    }

}
