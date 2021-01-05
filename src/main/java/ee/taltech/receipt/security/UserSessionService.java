package ee.taltech.receipt.security;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class UserSessionService implements UserDetailsService {

    private final CustomerRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> users = usersRepository.findAllByEmail(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException(format("Username not found: %s", username));
        }
        if (users.size() > 1) {
            throw new IllegalStateException(format("Too many users were found: %s", username));
        }

        Customer user = users.get(0);

        return new SessionUser(
            user.getEmail(),
            user.getPassword(),
            getAuthorities(user),
            user.getId(),
            user.getRole()
        );
    }

    public SessionUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof SessionUser) {
            return (SessionUser) principal;
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Customer user) {
        return getRoles(user)
            .map(role -> role.name)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    private Stream<Role> getRoles(Customer user) {
        if (Role.ADMIN.equals(user.getRole())) {
            return Arrays.stream(Role.values());
        }
        return Stream.of(user.getRole());
    }

}
