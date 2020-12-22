package ee.taltech.receipt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        long currentTimeMs = System.currentTimeMillis();

        return Jwts.builder()
            .setClaims(new HashMap<>()).setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(currentTimeMs))
            .setExpiration(new Date(currentTimeMs + jwtConfig.getDurationMillis()))
            .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
            .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return getUsernameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

}
