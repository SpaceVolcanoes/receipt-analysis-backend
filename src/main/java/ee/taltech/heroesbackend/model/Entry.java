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
public class Entry {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long cost;
    private Long receiptId;
    private String category;
    private Long quantity;

}
