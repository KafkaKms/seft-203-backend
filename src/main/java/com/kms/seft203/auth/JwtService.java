package com.kms.seft203.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwtSecret}")
    private String jwtSecret;

    public String generateJwtToken(User user) {
        var algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.create()
                .withIssuer("kms")
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("fullName", user.getFullName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (3 * 24 * 60 * 60 * 1000)))
                .sign(algorithm);
    }

    public String generateRefreshToken() {
        return RandomStringUtils.random(80);
    }

    public DecodedJWT decodeJwtToken(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer("kms")
                .build()
                .verify(token);
    }
}
