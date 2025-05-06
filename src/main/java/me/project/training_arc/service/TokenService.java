package me.project.training_arc.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.project.training_arc.dto.JwtToken;
import me.project.training_arc.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenService {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    @Value("${jwt.expirationMsRefresh}")
    private long expirationMsRefresh;


    public JwtToken generateToken(final Credentials credentials) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", credentials.getUsername());
        claims.put("email", credentials.getEmail());
        claims.put("iat", new Date().getTime());
        String access = createToken(claims, true);
        String refresh = createToken(claims, false);

        return new JwtToken(access, refresh);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private String createToken(Map<String, Object> claims, boolean isAccess) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (isAccess? expirationMs : expirationMsRefresh)))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("username")).toString();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()).build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = extractExpiration(token);
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token) {
        final String username = extractUsername(token);
        return (username != null && !isTokenExpired(token));
    }

    public JwtToken refreshToken(JwtToken token) {
        String username = null;

        try {
            if (isTokenExpired(token.getAccess())) {
                username = extractUsername(this.extractUsername(token.getAccess()));
            } else {
                return token;
            }
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            username = (String) claims.get("username");
            if (username != null) {
                Credentials user = (Credentials) myUserDetailsService.loadUserByUsername(username);
                if (user != null) {
                    return generateToken(user);
                }
            }
        }

        return token;

    }
}
