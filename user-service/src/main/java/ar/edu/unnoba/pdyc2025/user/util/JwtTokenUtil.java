package ar.edu.unnoba.pdyc2025.user.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final String SECRET = "secretkey1234";
    private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);
    private static final long EXPIRATION = 10 * 24 * 60 * 60 * 1000L; // 10 días

    public String generateToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .sign(ALGORITHM);
    }

    public boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            verifier.verify(token.trim());
            return true;
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid JWT: " + e.getMessage());
        }
    }

    public String getSubject(String token) {
        DecodedJWT decoded = JWT.require(ALGORITHM).build().verify(token.trim());
        return decoded.getSubject();
    }
}