package ee.taltech.receipt.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@ApiModel(value = "ReceiptSummary", description = "Summary about a Receipt")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ReceiptSummary {

    @ApiModelProperty(example = "1", accessMode = READ_ONLY, position = 0)
    private Long id;

    @ApiModelProperty(example = "2")
    private Long customerId;

    @ApiModelProperty(example = "Maxima")
    private String issuer;

    @ApiModelProperty(example = "2019-07-25 21:23:33.000")
    private Timestamp issuedAt;

    @ApiModelProperty(example = "2019-07-26 10:52:23.564")
    private Timestamp createdAt;

    @ApiModelProperty(example = "2019-08-01 15:37:10.385")
    private Timestamp modifiedAt;

    @ApiModelProperty(example = "7")
    private Long numberOfEntries;

    @ApiModelProperty(example = "43.6")
    private Double totalCostOfEntries;

    public ReceiptSummary(Receipt receipt) {
        id = receipt.getId();
        customerId = receipt.getCustomer().getId();
        issuer = receipt.getIssuer();
        issuedAt = receipt.getIssuedAt();
        createdAt = receipt.getCreatedAt();
        modifiedAt = receipt.getModifiedAt();
        numberOfEntries = (long) receipt.getEntries().size();
        totalCostOfEntries = calculateTotalCostOfEntries(receipt.getEntries());
    }

    private Double calculateTotalCostOfEntries(List<Entry> entries) {
        return entries.stream()
            .mapToDouble(e -> e.getCost() == null ? 0 : e.getCost())
            .sum();
    }

}
