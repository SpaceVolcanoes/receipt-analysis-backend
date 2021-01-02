package ee.taltech.receipt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;

import static java.net.URLDecoder.decode;

@Getter
@Setter
@ApiModel(value = "File", description = "Data describing a file")
public class Base64File {

    @ApiModelProperty(example = "data:image/png;base64")
    private String type;

    @ApiModelProperty(example = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVQYV2NgYAAAAAMAAWgmWQ0AAAAASUVORK5CYII=")
    private String data;

    public String getExtension() {
        String raw = decode(type, StandardCharsets.UTF_8);
        switch (raw) {
            case "data:image/jpeg;base64":
                return "jpeg";
            case "data:image/png;base64":
                return "png";
            default:
                return "jpg";
        }
    }

    public byte[] getBytes() {
        return DatatypeConverter.parseBase64Binary(data);
    }

}
