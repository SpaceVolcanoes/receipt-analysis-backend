package ee.taltech.receipt.controller;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.security.LoginDto;
import ee.taltech.receipt.security.LoginResponse;
import ee.taltech.receipt.security.AuthenticationService;
import ee.taltech.receipt.security.Role;
import ee.taltech.receipt.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("auth")
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationService authService;
    private final CustomerService customerService;

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }

    @PostMapping("register")
    public LoginResponse register(@RequestBody Customer customer){
        String originalPassword = customer.getPassword();
        customer.setRole(Role.USER);
        customer = customerService.create(customer);

        return authService.login(
            new LoginDto()
                .setUsername(customer.getEmail())
                .setPassword(originalPassword)
        );
    }

}
