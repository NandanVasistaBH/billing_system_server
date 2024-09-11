package com.telstra.billing_system.service;

import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.model.User;
import com.telstra.billing_system.repository.SupplierRepository;
import com.telstra.billing_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SupplierServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private SupplierRepository supplierRepo;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private SupplierService supplierService;

    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        encoder = new BCryptPasswordEncoder(10);
    }

    @Test
    void testRegister_Success() {
        Supplier supplier = new Supplier();
        User user = new User();
        user.setName("supplier1");
        user.setPassword("password123");
        supplier.setUser(user);

        when(userRepo.save(any(User.class))).thenReturn(user);
        when(supplierRepo.save(any(Supplier.class))).thenReturn(supplier);
        when(jwtService.generateToken(user.getName())).thenReturn("mockToken");

        String result = supplierService.register(supplier);

        assertEquals("mockToken", result);
        verify(userRepo, times(1)).save(any(User.class));
        verify(supplierRepo, times(1)).save(any(Supplier.class));
        verify(jwtService, times(1)).generateToken(user.getName());
    }

    @Test
    void testRegister_Exception() {
        Supplier supplier = new Supplier();
        User user = new User();
        user.setName("supplier1");
        user.setPassword("password123");
        supplier.setUser(user);

        when(userRepo.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        String result = supplierService.register(supplier);

        assertEquals("Database error", result);
        verify(userRepo, times(1)).save(any(User.class));
        verify(supplierRepo, never()).save(any(Supplier.class));
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void testVerify_Success() {
        Supplier supplier = new Supplier();
        User user = new User();
        user.setName("supplier1");
        user.setPassword("encodedPassword");
        supplier.setUser(user);

        Authentication mockAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(user.getName())).thenReturn("mockToken");

        String result = supplierService.verify(supplier);

        assertEquals("mockToken", result);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(user.getName());
    }

    @Test
    void testVerify_Failure() {
        Supplier supplier = new Supplier();
        User user = new User();
        user.setName("supplier1");
        user.setPassword("wrongPassword");
        supplier.setUser(user);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        String result = supplierService.verify(supplier);

        assertEquals("failure", result);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(anyString());
    }
}
