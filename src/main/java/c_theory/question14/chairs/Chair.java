package c_theory.question14.chairs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Chair {

    private String name;
    private String type;
    private LocalDate released;
    private BigDecimal price;
    private BigDecimal regularPrice;
//    ... many more
    private Designer designer;
}
