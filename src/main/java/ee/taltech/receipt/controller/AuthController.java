package ee.taltech.receipt.controller;

import ee.taltech.receipt.security.LoginDto;
import ee.taltech.receipt.security.LoginResponse;
import ee.taltech.receipt.security.AuthenticationService;
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

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }

}
