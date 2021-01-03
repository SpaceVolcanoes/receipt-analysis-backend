package ee.taltech.receipt.security;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSessionServiceTest {

    @Mock
    private CustomerRepository usersRepository;

    @InjectMocks
    private UserSessionService service;

    @Test
    void loadUserByUsernameThrowsWhenNoneFound() {
        when(usersRepository.findAllByEmail("missing a name")).thenReturn(List.of());

        Throwable thrown = catchThrowable(() -> service.loadUserByUsername("missing a name"));

        assertThat(thrown)
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("Username not found: missing a name");
    }

    @Test
    void loadUserByUsernameThrowsWhenTooManyFound() {
        when(usersRepository.findAllByEmail("many")).thenReturn(List.of(new Customer(), new Customer()));

        Throwable thrown = catchThrowable(() -> service.loadUserByUsername("many"));

        assertThat(thrown)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Too many users were found: many");
    }

    @Test
    void loadUserByUsernameReturnsAdminWithAllRoles() {
        Customer user = new Customer()
            .setEmail("user")
            .setPassword("pass")
            .setRole(Role.ADMIN)
            .setId(3L);

        when(usersRepository.findAllByEmail("matching")).thenReturn(List.of(user));

        UserDetails actual = service.loadUserByUsername("matching");
        List<String> roles = actual.getAuthorities().stream().map(Object::toString).collect(Collectors.toList());

        assertThat(actual.getUsername()).isEqualTo("user");
        assertThat(actual.getPassword()).isEqualTo("pass");
        assertThat(roles).containsExactlyInAnyOrder("ROLE_ADMIN", "ROLE_USER", "ROLE_GUEST");
    }

}
