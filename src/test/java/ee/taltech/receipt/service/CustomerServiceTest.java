package ee.taltech.receipt.service;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.repository.CustomerRepository;
import ee.taltech.receipt.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepostiory;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createThrowsIfIdPresent(){
        Throwable thrown = catchThrowable(() -> customerService.create(new Customer().setId(6L)));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Attempting to re-create an existing Customer with ID 6");
    }

    @Test
    void createThrowsIfEmailInRepository(){
        when(customerRepostiory.findAllByEmail("test@gmail.com"))
            .thenReturn(Arrays.asList(new Customer(), new Customer()));
        Throwable thrown = catchThrowable(() -> customerService.create(new Customer().setEmail("test@gmail.com")));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Attempting to create a Customer with an existing email: test@gmail.com");
    }

    @Test
    void createReturnsCustomerAndDecodesPassword(){
        Customer customer = new Customer()
            .setName("Kristjan")
            .setPassword("password")
            .setEmail("krissu@ttu.ee")
            .setRole(Role.USER);

        when(customerRepostiory.findAllByEmail("krissu@ttu.ee")).thenReturn(Collections.emptyList());
        when(passwordEncoder.encode("password")).thenReturn("Encoded password");
        when(customerRepostiory.save(customer)).thenReturn(customer);

        Customer returned = customerService.create(customer);

        assertThat(returned.getName()).isEqualTo("Kristjan");
        assertThat(returned.getPassword()).isEqualTo("Encoded password");
        assertThat(returned.getEmail()).isEqualTo("krissu@ttu.ee");
        assertThat(returned.getRole()).isEqualTo(Role.USER);
    }

}
