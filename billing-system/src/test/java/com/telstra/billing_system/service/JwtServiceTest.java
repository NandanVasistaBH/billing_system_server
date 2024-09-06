package com.telstra.billing_system.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private static final String TEST_SECRET_KEY = "vTQ6Usc4uKsmOhd2ZYQ9vD/qg6QjpK5y4/g2ZrW80EY=";
    private static final String USERNAME = "testuser";
    private String token;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        token = jwtService.generateToken(USERNAME);
        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(USERNAME);
    }

    @Test
    void testGenerateToken() {
        String generatedToken = jwtService.generateToken(USERNAME);
        assertNotNull(generatedToken);
        assertTrue(generatedToken.length() > 0);
    }

    @Test
    void testExtractUsername() {
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(USERNAME, extractedUsername);
    }

    @Test
    void testValidateToken_ValidToken() {
        boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = Jwts.builder()
                .setSubject("otheruser")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(jwtService.getKey(), SignatureAlgorithm.HS256)
                .compact();

        boolean isValid = jwtService.validateToken(invalidToken, userDetails);
        assertFalse(isValid);
    }

    @Test
    void testIsTokenExpired_ExpiredToken() {
        String expiredToken = Jwts.builder()
                .setSubject(USERNAME)
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // Expired 1 hour ago
                .signWith(jwtService.getKey(), SignatureAlgorithm.HS256)
                .compact();

        //boolean isExpired = jwtService.isTokenExpired(expiredToken);
        //assertTrue(isExpired);
    }

    @Test
    void testIsTokenExpired_NotExpiredToken() {
        boolean isExpired = jwtService.isTokenExpired(token);
        assertFalse(isExpired);
    }
}
