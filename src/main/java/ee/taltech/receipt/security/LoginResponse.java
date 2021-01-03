package ee.taltech.receipt.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

    private String username;
    private String token;
    private Role role;
    private Long id;

}
