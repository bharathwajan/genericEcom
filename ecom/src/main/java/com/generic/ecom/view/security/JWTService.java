package com.generic.ecom.view.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JWTService {
    /*
    Purpose is to generate the JWT token.
     */

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

    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject); // Claims::getSubject gets the username from the claims(the claims consist of  userName, iat and exp)
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token); // gets the claims(the claims are userName, iat and exp)
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token) // ⬅️ Parses and verifies the signed JWT
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // get the expiration date from the claims
    }

}