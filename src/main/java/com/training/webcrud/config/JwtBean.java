package com.training.webcrud.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class JwtBean {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.minutesToExpire}")
    private Integer minutesToExpire;

    public String generateToken(String username, Object content) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, minutesToExpire);

        return Jwts.builder()
                .setExpiration(instance.getTime())
                .claim("content", content)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        Object claims = Jwts.parser()
                .setSigningKey(secret)
                .parse(token)
                .getBody();

        return claims != null;
    }

}
