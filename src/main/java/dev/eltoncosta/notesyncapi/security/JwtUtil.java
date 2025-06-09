package dev.eltoncosta.notesyncapi.security;

import dev.eltoncosta.notesyncapi.controllers.response.UserDetailsResponse;
import dev.eltoncosta.notesyncapi.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET = "super-secret-key-para-jwt-que-deve-ser-bem-grande-e-segura";
    private final long EXPIRATION = 60 * 60 * 24000; // 24 hora em ms
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("nome", usuario.getNome())
                .claim("idEstudante", usuario.getIdEstudante())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public UserDetailsResponse extractUserDetails(String token) {
        Claims claims = extractAllClaims(token);
        return new UserDetailsResponse(
            claims.get("id", Long.class),
            claims.get("nome", String.class),
            claims.getSubject(), // email
            claims.get("idEstudante", String.class)
        );
    }
}
