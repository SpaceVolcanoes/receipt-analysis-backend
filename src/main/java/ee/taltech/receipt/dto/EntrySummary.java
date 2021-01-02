package ee.taltech.receipt.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ee.taltech.receipt.model.Entry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@ApiModel(value = "EntrySummary", description = "Summary about an Entry")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EntrySummary {

    @ApiModelProperty(example = "1", accessMode = READ_ONLY, position = 0)
    private Long id;

    @ApiModelProperty(example = "2")
    private Long receiptId;

    @ApiModelProperty(example = "2.6")
    private Double cost;

    @ApiModelProperty(example = "10")
    private Long quantity;

    @ApiModelProperty(example = "Groceries")
    private String category;

    @ApiModelProperty(example = "Milk")
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
