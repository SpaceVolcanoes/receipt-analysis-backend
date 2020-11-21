package ee.taltech.receipt.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Base64FileTest {

    @Test
    void getExtension() {
        String jpeg = new Base64File().setType("data:image/jpeg;base64").getExtension();
        String png = new Base64File().setType("data:image/png;base64").getExtension();
        String jpg = new Base64File().setType("data:image/jpg;base64").getExtension();

        assertThat(jpeg).isEqualTo("jpeg");
        assertThat(png).isEqualTo("png");
        assertThat(jpg).isEqualTo("jpg");
    }

    @Test
    void getBytes() {
        String given = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVQYV2NgYAAAAAMAAWgmWQ0AAAAASUVORK5CYII=";
        String expected = "89504E470D0A1A0A0000000D4948445200000001000000010804000000B51C0C020000000B4944415418576360600000000300016826590D0000000049454E44AE426082";

        byte[] bytes = new Base64File().setData(given).getBytes();

        assertThat(bytes).asHexString().isEqualTo(expected);
    }

}
