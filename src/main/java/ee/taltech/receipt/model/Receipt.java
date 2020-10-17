package ee.taltech.receipt.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Receipt {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;

    private String fileName;

    private String issuer;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entry> entries;

    @CreationTimestamp
    private Timestamp issuedAt;

    @CreationTimestamp
    private Timestamp createdAt;

    @CreationTimestamp
    private Timestamp modifiedAt;

}
