package com.kms.seft203.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
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
                .withClaim("username", user.getUsername())
                .withClaim("fullName", user.getFullName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                .sign(algorithm);
    }

    public String decodeJwtToken(String token) {
	    var decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer("kms")
                .build()
                .verify(token);

        return decodedJWT.getClaim("username").asString();
    }

    public boolean validate(String token) {

        var decode = JWT.decode(token);
        var algorithm = Algorithm.HMAC256(jwtSecret);
        try {
            algorithm.verify(decode);
        } catch (SignatureVerificationException ignored) {
            return false;
        }

        return true;
    }
}
