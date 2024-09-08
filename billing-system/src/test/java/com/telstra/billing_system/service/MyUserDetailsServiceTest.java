package com.telstra.billing_system.service;

import com.telstra.billing_system.model.User;
import com.telstra.billing_system.model.UserPrincipal;
import com.telstra.billing_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        User user = new User();
        user.setName("Sam");
        user.setPassword("password123");

        when(userRepo.findByName("Sam")).thenReturn(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("Sam");

        assertNotNull(userDetails);
        assertEquals("Sam", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());

        verify(userRepo, times(1)).findByName("Sam");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepo.findByName(anyString())).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(
            UsernameNotFoundException.class, 
            () -> myUserDetailsService.loadUserByUsername("non_existing_user")
        );

        assertEquals("User not found with username: non_existing_user", exception.getMessage());
        verify(userRepo, times(1)).findByName("non_existing_user");
    }
}
