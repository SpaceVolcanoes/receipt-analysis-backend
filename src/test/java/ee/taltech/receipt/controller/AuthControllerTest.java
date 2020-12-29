package ee.taltech.receipt.controller;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.security.AuthenticationService;
import ee.taltech.receipt.security.LoginDto;
import ee.taltech.receipt.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationService authService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AuthController authController;

    @Test
    void registerCallsAuthWithLoginDto(){
        Customer customer = new Customer()
            .setName("Kristjan")
            .setEmail("krissu@ttu.ee")
            .setPassword("password");

        when(customerService.create(customer)).thenReturn(customer);

        authController.register(customer);

        ArgumentCaptor<LoginDto> captor = ArgumentCaptor.forClass(LoginDto.class);
        verify(authService).login(captor.capture());
        LoginDto captured = captor.getValue();

        assertThat(captured.getUsername()).isEqualTo("krissu@ttu.ee");
        assertThat(captured.getPassword()).isEqualTo("password");

    }
}
