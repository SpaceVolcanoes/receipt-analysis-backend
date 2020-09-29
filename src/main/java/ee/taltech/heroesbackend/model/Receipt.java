package ee.taltech.heroesbackend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Receipt {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String fileName;

    private String issuer;

    @CreationTimestamp
    private Timestamp issuedAt;

    @CreationTimestamp
    private Timestamp createdAt;

    @CreationTimestamp
    private Timestamp modifiedAt;

}
