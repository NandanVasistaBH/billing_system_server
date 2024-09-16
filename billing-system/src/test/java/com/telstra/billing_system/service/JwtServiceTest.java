package com.telstra.billing_system.service;
 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
 
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class JwtServiceTest {
 
    @InjectMocks
    private JwtService jwtService;
 
    @Mock
    private UserDetails userDetails;
 
    private static final String SECRET_KEY = "vTQ6Usc4uKsmOhd2ZYQ9vD/qg6QjpK5y4/g2ZrW80EY=";
    private static final String USERNAME = "admin";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyNjQ2NDgzNiwiZXhwIjoxNzI2NTUxMjM2fQ.3a-bpB3JOFVhtmYcy-OSsEEbOQHnFLz9MSP3sW7MBRM";
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
    }
 
    @Test
void testGenerateToken() {
    String token = jwtService.generateToken("testUser");
    assertNotNull(token);
    String extractedUsername = jwtService.extractUsername(token);
    assertEquals("testUser", extractedUsername);
}
 
    @Test
    void testExtractUsername() {
    String token = jwtService.generateToken(USERNAME);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(USERNAME, extractedUsername);
    }
 
    @Test
    void testValidateToken() {
        when(userDetails.getUsername()).thenReturn(USERNAME);
        String token = jwtService.generateToken(USERNAME);
        boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);
    }
 
    @Test
    void testIsTokenExpired() {
        String token = jwtService.generateToken(USERNAME);
        boolean isExpired = jwtService.isTokenExpired(token);
        assertFalse(isExpired); // Token should not be expired immediately after creation
    }
 
    @Test
    void testExtractExpirationDate() {
        String token = jwtService.generateToken(USERNAME);
        Date expirationDate = jwtService.extractExpirationDate(token);
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date())); // Expiration date should be in the future
    }
   
    @Test
    void testExtractAllClaims() {
        String token = jwtService.generateToken(USERNAME);
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims);
        assertEquals(USERNAME, claims.getSubject());
    }
   
    
}