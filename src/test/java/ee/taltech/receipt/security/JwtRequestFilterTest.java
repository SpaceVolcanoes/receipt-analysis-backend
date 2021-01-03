package ee.taltech.receipt.security;

import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static ee.taltech.receipt.security.JwtRequestFilter.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtRequestFilterTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @Mock
    private UserSessionService userSessionService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Logger logger;

    @InjectMocks
    private SpyFilter filter;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test
    void doFilterInternalSkipsIfNoToken() throws ServletException, IOException {
        when(request.getHeader(AUTHORIZATION)).thenReturn("bad prefix");

        filter.invokeDoFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verifyNoInteractions(jwtTokenProvider);
    }

    @Test
    void doFilterInternalSkipsIfNoUsername() throws ServletException, IOException {
        SignatureException exception = new SignatureException("invalid");

        when(request.getHeader(AUTHORIZATION)).thenReturn("Bearer token");
        when(jwtTokenProvider.getUsernameFromToken("token")).thenThrow(exception);

        filter.invokeDoFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(jwtTokenProvider).getUsernameFromToken("token");
        verify(logger).warn("JWT Token has a faulty signature", exception);
        verifyNoInteractions(userSessionService);
    }

    @Test
    void doFilterInternalSetsTokenToContext() throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(null);
        UserDetails user = new SessionUser("name", "pass", Collections.emptyList(), 3L, Role.USER);

        when(request.getHeader(AUTHORIZATION)).thenReturn("Bearer token");
        when(jwtTokenProvider.getUsernameFromToken("token")).thenReturn("user");
        when(userSessionService.loadUserByUsername("user")).thenReturn(user);
        when(jwtTokenProvider.validateToken("token", user)).thenReturn(true);

        filter.invokeDoFilterInternal(request, response, chain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertThat(authentication.getPrincipal()).isSameAs(user);
        assertThat(authentication.isAuthenticated()).isTrue();

        verify(chain).doFilter(request, response);
        verifyNoInteractions(logger);
    }

}
