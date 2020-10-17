package ee.taltech.receipt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralStatistics {

    private Long customerAmount;
    private Long receiptAmount;
    private Long entryAmount;
    private Double entriesPerReceipt;

    public GeneralStatistics(
        Long customerAmount,
        Long receiptAmount,
        Long entryAmount
    ) {
        this.customerAmount = customerAmount;
        this.receiptAmount = receiptAmount;
        this.entryAmount = entryAmount;
        this.entriesPerReceipt = entryAmount.doubleValue() / receiptAmount.doubleValue();
    }

}
