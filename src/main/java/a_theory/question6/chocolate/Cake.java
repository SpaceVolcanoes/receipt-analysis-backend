package a_theory.question6.chocolate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@ApiModel(value = "Cake", description = "Cakes from Chocolate, is the best cafe for cakes")
@Getter
public class Cake {

    @ApiModelProperty(example = "1", accessMode = READ_ONLY, position = 0)
    private Long id;

    @ApiModelProperty(required = true, example = "Sachertorte")
    private String name;

    @ApiModelProperty(required = true, example = "big", allowableValues = "big, small")
    private String size;

    @ApiModelProperty(required = true, example = "sweet", allowableValues = "medium, sweet")
    private String sweetness;

    @ApiModelProperty(required = false, example = "[Sugar, Milk]")
    private List<String> ingredients;

    @ApiModelProperty(required = false, example = "[Almonds, Strawberries]")
    private List<String> toppings;

}
