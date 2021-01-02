package ee.taltech.receipt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "GeneralStatistics", description = "Statistics about our data in general")
@Getter
@Setter
public class GeneralStatistics {

    @ApiModelProperty(example = "412")
    private Long customerAmount;

    @ApiModelProperty(example = "5562")
    private Long receiptAmount;

    @ApiModelProperty(example = "92421")
    private Long entryAmount;

    @ApiModelProperty(example = "16.6")
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
