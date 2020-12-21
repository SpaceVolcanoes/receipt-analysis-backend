package ee.taltech.receipt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.List;

import static java.util.Collections.singletonList;
import static ee.taltech.receipt.security.JwtRequestFilter.AUTHORIZATION;

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

        return withJwtLogin(builder)
            .select()
            .apis(RequestHandlerSelectors.basePackage("ee.taltech"))
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * Adds a login form with JWT token for Swagger, manually have to prepend Bearer to the token
     */
    private Docket withJwtLogin(Docket docket) {
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = new AuthorizationScope("global", "accessEverything");
        List<SecurityReference> defaultAuth = singletonList(new SecurityReference("JWT", scopes));

        SecurityContext context = SecurityContext.builder().securityReferences(defaultAuth).build();

        return docket
            .securityContexts(singletonList(context))
            .securitySchemes(singletonList(new ApiKey("JWT", AUTHORIZATION, "header")));
    }

}
