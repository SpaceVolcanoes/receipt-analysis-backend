package ee.taltech.receipt.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secret;
    private int durationMin;

    public int getDurationMillis() {
        return durationMin * 60 * 1000;
    }

}
