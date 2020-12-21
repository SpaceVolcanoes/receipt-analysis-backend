package ee.taltech.receipt.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class SessionUser extends User {

    private final Long id;
    private final Role role;

    public SessionUser(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        Long id,
        Role role
    ) {
        super(username, password, authorities);
        this.id = id;
        this.role = role;
    }

}
