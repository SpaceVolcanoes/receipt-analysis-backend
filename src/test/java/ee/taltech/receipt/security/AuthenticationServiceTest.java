package ee.taltech.receipt.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthenticationService service;

    @Test
    void loginThrowsIfMissingUsername() {
        Throwable thrown = catchThrowable(() -> service.login(new LoginDto()));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Missing username");
    }

    @Test
    void loginThrowsIfMissingPassword() {
        Throwable thrown = catchThrowable(() -> service.login(new LoginDto().setUsername("test name")));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Missing password");
    }

    @Test
    void loginReturnsWithToken() {
        LoginDto credentials = new LoginDto().setUsername("name").setPassword("pass");

        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        Authentication authentication = mock(Authentication.class);
        SessionUser user = new SessionUser("name", "pass", Collections.emptyList(), 3L, Role.USER);

        when(authenticationManager.authenticate(captor.capture())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtTokenProvider.generateToken(user)).thenReturn("token");

        LoginResponse actual = service.login(credentials);

        assertThat(actual.getUsername()).isEqualTo("name");
        assertThat(actual.getToken()).isEqualTo("token");
        assertThat(actual.getRole()).isEqualTo(Role.USER);
        assertThat(actual.getId()).isEqualTo(3L);
    }

}
