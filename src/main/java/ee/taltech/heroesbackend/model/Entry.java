package ee.taltech.heroesbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Entry {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Receipt receipt;

    private String name;

    private Double cost;

    private Long quantity;

    private String category;

    @CreationTimestamp
    private Timestamp createdAt;

    @CreationTimestamp
    private Timestamp modifiedAt;

}
