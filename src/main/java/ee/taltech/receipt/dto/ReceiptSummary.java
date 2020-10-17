package ee.taltech.receipt.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ReceiptSummary {

    private Long id;
    private Long customerId;
    private String issuer;
    private Timestamp issuedAt;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Long numberOfEntries;
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
            .mapToDouble(Entry::getCost)
            .sum();
    }

}
