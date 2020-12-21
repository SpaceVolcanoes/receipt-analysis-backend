package ee.taltech.receipt.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginDto login) {
        if (isBlank(login.getUsername())) {
            throw new IllegalArgumentException("Missing username");
        }
        if (isBlank(login.getPassword())) {
            throw new IllegalArgumentException("Missing password");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            login.getUsername(),
            login.getPassword()
        ));
        SessionUser user = (SessionUser) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(user);

        return LoginResponse.builder()
            .username(user.getUsername())
            .token(token)
            .role(user.getRole())
            .id(user.getId())
            .build();
    }

}
