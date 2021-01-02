package ee.taltech.receipt.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@ApiModel(value = "Entry", description = "An item on a receipt")
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Entry {

    @ApiModelProperty(example = "1", accessMode = READ_ONLY, position = 0)
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Receipt receipt;

    @ApiModelProperty(example = "Milk")
    private String name;

    @ApiModelProperty(example = "2.0")
    private Double cost;

    @ApiModelProperty(example = "2")
    private Long quantity;

    @ApiModelProperty(example = "Groceries")
    private String category;

    @ApiModelProperty(example = "2019-07-26 10:52:23.564")
    @CreationTimestamp
    private Timestamp createdAt;

    @ApiModelProperty(example = "2019-08-01 15:37:10.385")
    @CreationTimestamp
    private Timestamp modifiedAt;

}
