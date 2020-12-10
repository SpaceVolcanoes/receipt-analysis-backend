package ee.taltech.receipt.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

import java.time.Duration;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Configuration
public class RestConfiguration {

    private final UriTemplateHandler uriHandler;

    public RestConfiguration(Environment environment) {
        this.uriHandler = new DefaultUriBuilderFactory(environment.getProperty("OCR_URL", "http://localhost:8800/"));
    }

    @Bean("OcrTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .defaultHeader(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
            .uriTemplateHandler(uriHandler)
            .setConnectTimeout(Duration.ofMillis(3000))
            .setReadTimeout(Duration.ofMillis(30000))
            .build();
    }

}
