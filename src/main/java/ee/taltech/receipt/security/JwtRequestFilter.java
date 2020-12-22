package ee.taltech.receipt.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final UserSessionService userSessionService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger logger;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        String jwtToken = getToken(request);
        if (jwtToken == null) {
            chain.doFilter(request, response);
            return;
        }

        String username = getUsername(jwtToken);
        if (username == null) {
            chain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userSessionService.loadUserByUsername(username);

            if (jwtTokenProvider.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader(AUTHORIZATION);
        if (requestTokenHeader == null || !requestTokenHeader.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return requestTokenHeader.substring(7);
    }

    private String getUsername(String jwtToken) {
        try {
            return jwtTokenProvider.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException exception) {
            logger.error("Unable to get JWT Token", exception);
        } catch (ExpiredJwtException exception) {
            logger.warn("JWT Token has expired", exception);
        } catch (SignatureException exception) {
            logger.warn("JWT Token has a faulty signature", exception);
        } catch (Exception exception) {
            logger.error("JWT Token has unexpected error", exception);
        }
        return null;
    }

}
