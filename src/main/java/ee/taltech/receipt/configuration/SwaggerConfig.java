package ee.taltech.receipt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final Environment environment;

    public SwaggerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Docket api() {
        Docket builder = new Docket(DocumentationType.SWAGGER_2);

        String host = environment.getProperty("host");
        if (host != null && !host.isEmpty()) {
            builder.host(host);
        }

        String path = environment.getProperty("basepath");
        if (path != null && !path.isEmpty()) {
            builder.pathMapping(path);
        }

        return builder.select()
            .apis(RequestHandlerSelectors.basePackage("ee.taltech"))
            .paths(PathSelectors.any())
            .build();
    }

}
