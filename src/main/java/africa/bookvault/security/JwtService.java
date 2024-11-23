package africa.bookvault.security;

import africa.bookvault.exception.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Getter
@Setter
@Component
public class JwtService {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private String userName;

    public String extractUserName(String token) {
        userName = extractClaim(token, Claims::getSubject);
        return userName;
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails) {
        String compact;
        try {
            compact = Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    //JSON Token Expires in 5hours
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 300))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();

        } catch (SignatureException exception) {
            throw new BadRequestException(exception.getMessage());
        }
        return compact;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        boolean isValid;
        try {
            isValid = userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (SignatureException exception) {
            throw new SignatureException(exception.getMessage());
        }
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        Claims resolvedClaims;
        try {
            resolvedClaims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException exception) {
            throw new SignatureException(exception.getMessage());
        }
        return resolvedClaims;
    }

    private Key getSignInKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }
}