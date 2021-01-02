package ee.taltech.receipt.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ee.taltech.receipt.security.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@ApiModel(value = "Customer", description = "Someone who uses our application")
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer {

    @ApiModelProperty(example = "1", accessMode = READ_ONLY, position = 0)
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(example = "Kristjan")
    private String name;

    @ApiModelProperty(example = "mart@ttu.ee")
    private String email;

    @ApiModelProperty(example = "password")
    private String password;

    @ApiModelProperty(example = "2019-07-26 10:52:23.564")
    @CreationTimestamp
    private Timestamp createdAt;

    @ApiModelProperty(example = "2019-08-01 15:37:10.385")
    @CreationTimestamp
    private Timestamp modifiedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receipt> receipts;

    @ApiModelProperty(example = "ROLE_USER")
    @Enumerated(EnumType.STRING)
    private Role role;

}
