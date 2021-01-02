package ee.taltech.receipt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "OcrMessage", description = "Message from ocr service")
@Getter
@Setter
public class OcrMessage {

    @ApiModelProperty(example = "Groceries")
    private String text;

    @ApiModelProperty(example = "Success")
    private String message;

}
