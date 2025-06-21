package com.generic.ecom.view.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Service
public class JWTService {

    @Value("${spring.security.jwt.secret}")
    private String secretKey;
    // I prefer using the secret key from the applciation instead of generating it dynamically. --> for security purposes and for the deployment ease .

//    public JWTService(){
//        try {
//            KeyGenerator hmacSHA256 = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = hmacSHA256.generateKey();
//            secretKey=Base64.getEncoder().encodeToString(sk.getEncoded()); // instead we can our own key as well.
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public String generateToken(String userName) {
        System.out.println("Key used to generate token : "+secretKey);
        Map<String,Object> claims=new HashMap<>();
        String expiration = Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60* 2))
                .and()
                .signWith(getKey())
                .compact();
        // 30 minutes;
        return expiration;
    }

    public Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}