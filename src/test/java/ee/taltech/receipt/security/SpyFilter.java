package ee.taltech.receipt.security;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SpyFilter extends JwtRequestFilter {

    public SpyFilter(UserSessionService userSessionService, JwtTokenProvider jwtTokenProvider, Logger logger) {
        super(userSessionService, jwtTokenProvider, logger);
    }

    public void invokeDoFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        doFilterInternal(request, response, chain);
    }

}
