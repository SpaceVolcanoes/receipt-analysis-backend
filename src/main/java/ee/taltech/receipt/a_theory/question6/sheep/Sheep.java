package ee.taltech.receipt.a_theory.question6.sheep;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Sheep {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer age;
    private String color;
}
