package ee.taltech.receipt.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ee.taltech.receipt.model.Entry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EntrySummary {

    private Long id;
    private Long receiptId;
    private Double cost;
    private Long quantity;
    private String category;
    private String name;

    public EntrySummary(Entry entry) {
        id = entry.getId();
        receiptId = entry.getReceipt().getId();
        cost = entry.getCost();
        quantity = entry.getQuantity();
        category = entry.getCategory();
        name = entry.getName();
    }

}
