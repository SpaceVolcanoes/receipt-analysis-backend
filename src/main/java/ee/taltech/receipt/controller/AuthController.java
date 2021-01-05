package ee.taltech.receipt.controller;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.security.LoginDto;
import ee.taltech.receipt.security.LoginResponse;
import ee.taltech.receipt.security.AuthenticationService;
import ee.taltech.receipt.security.Role;
import ee.taltech.receipt.service.CustomerService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("auth")
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationService authService;
    private final CustomerService customerService;

    @Role.Guest
    @PostMapping("login")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Login successful"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid credentials"),
    })
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @Role.Guest
    @PostMapping("register")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Registration successful"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Unable to create user with given params"),
    })
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        try {
            String originalPassword = customer.getPassword();
            customer.setRole(Role.USER);
            customer = customerService.create(customer);

            LoginDto credentials = new LoginDto().setUsername(customer.getEmail()).setPassword(originalPassword);
            LoginResponse response = authService.login(credentials);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
