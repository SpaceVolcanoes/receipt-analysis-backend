package ee.taltech.receipt.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private JwtConfig jwtConfig;

    @InjectMocks
    private JwtTokenProvider provider;

    @Test
    void generateTokenBuildsToken() throws IOException {
        SessionUser user = new SessionUser("uName", "pass", Collections.emptyList(), 3L, Role.USER);

        when(jwtConfig.getDurationMillis()).thenReturn(2000);
        when(jwtConfig.getSecret()).thenReturn("test");

        String[] actual = provider.generateToken(user).split("\\.");

        JsonNode algorithm = decodeJson(actual[0]);
        JsonNode payload = decodeJson(actual[1]);
        int diff = payload.get("exp").asInt() - payload.get("iat").asInt();

        assertThat(actual).hasSize(3);

        assertThat(algorithm.get("alg").asText()).isEqualTo("HS512");
        assertThat(payload.get("sub").asText()).isEqualTo("uName");
        assertThat(diff).isEqualTo(2);
    }

    private JsonNode decodeJson(String json) throws IOException {
        return mapper.readTree(Base64.getDecoder().decode(json));
    }

}
