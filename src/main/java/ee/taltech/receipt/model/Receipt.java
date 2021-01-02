package ee.taltech.receipt.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@ApiModel(value = "Receipt", description = "Describes the items on someone's receipt")
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Receipt {

    @ApiModelProperty(example = "1", accessMode = READ_ONLY, position = 0)
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;

    @ApiModelProperty(example = "picture.png")
    private String fileName;

    @ApiModelProperty(example = "Maxima")
    private String issuer;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entry> entries;

    @ApiModelProperty(example = "2019-07-25 21:23:33.000")
    @CreationTimestamp
    private Timestamp issuedAt;

    @ApiModelProperty(example = "2019-07-26 10:52:23.564")
    @CreationTimestamp
    private Timestamp createdAt;

    @ApiModelProperty(example = "2019-08-01 15:37:10.385")
    @CreationTimestamp
    private Timestamp modifiedAt;

}
