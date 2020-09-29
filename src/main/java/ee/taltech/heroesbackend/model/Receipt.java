package ee.taltech.heroesbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Receipt {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String modificationDate;
    private String creationData;
    private String issuer;

}
