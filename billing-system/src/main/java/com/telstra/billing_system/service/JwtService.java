package com.telstra.billing_system.service;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Map;
import java.util.function.Function;
import java.util.HashMap;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    
    public String generateToken(String username){
        Map<String,Object> claims= new HashMap<>();
        return Jwts.builder()
                    .claims()
                    .add(claims)
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+24 * 60 * 60 * 1000)) // 24 hours
                    .and()
                    .signWith(getKey())
                    .compact();
    }
    SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    public boolean validateToken(String token , UserDetails userDetails){
        final String userName = extractUsername(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    private <T> T extractClaim(String token,Function<Claims , T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }
    
    public boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }
    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    

   
}
