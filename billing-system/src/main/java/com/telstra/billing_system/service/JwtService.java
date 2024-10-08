package com.telstra.billing_system.service;

import javax.crypto.SecretKey;
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
    private static final String SECRET_KEY = "vTQ6Usc4uKsmOhd2ZYQ9vD/qg6QjpK5y4/g2ZrW80EY=";
    
    public String generateToken(String username){
        Map<String,Object> claims= new HashMap<>();
        return Jwts.builder()
                    .claims()
                    .add(claims)
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+60*60*60)) // 1hr
                    .and()
                    .signWith(getKey())
                    .compact();
    }
    private SecretKey getKey() {
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
    
    private boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }
    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

   
}
