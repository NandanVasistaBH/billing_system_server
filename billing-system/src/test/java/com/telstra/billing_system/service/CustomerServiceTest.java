package com.telstra.billing_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.User;
import com.telstra.billing_system.repository.CustomerRepository;
import com.telstra.billing_system.repository.UserRepository;

public class CustomerServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private CustomerService customerService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        // Arrange
        User user = new User();
        user.setName("testuser");
        user.setPassword("password");

        Customer customer = new Customer();
        customer.setUser(user);

        when(userRepo.save(any(User.class))).thenReturn(user);
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);
        when(jwtService.generateToken(user.getName())).thenReturn("mocked-jwt-token");

        // Act
        String result = customerService.register(customer);

        // Assert
        assertEquals("mocked-jwt-token", result);
        verify(userRepo, times(1)).save(any(User.class));
        verify(customerRepo, times(1)).save(any(Customer.class));
    }

    @Test
    public void testRegister_Exception() {
        // Arrange
        User user = new User();
        user.setName("testuser");
        user.setPassword("password");

        Customer customer = new Customer();
        customer.setUser(user);

        when(userRepo.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        // Act
        String result = customerService.register(customer);

        // Assert
        assertEquals("Database error", result);
    }

    @Test
    public void testVerify_Success() {
        // Arrange
        User user = new User();
        user.setName("testuser");
        user.setPassword("encodedPassword");

        Customer customer = new Customer();
        customer.setUser(user);

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(user.getName())).thenReturn("mocked-jwt-token");

        // Act
        String result = customerService.verify(customer);

        // Assert
        assertEquals("mocked-jwt-token", result);
    }

    @Test
    public void testVerify_Failure() {
        // Arrange
        User user = new User();
        user.setName("testuser");
        user.setPassword("wrongPassword");

        Customer customer = new Customer();
        customer.setUser(user);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Authentication failed"));

        // Act
        String result = customerService.verify(customer);

        // Assert
        assertEquals("failure", result);
    }

//     @Test
// public void testGetCustomerFromName_Success() {
//     // Arrange
//     Customer customer = new Customer();
//     customer.setName("testuser");
    
//     // Mock user object to avoid null pointer issues in CustomerDTO
//     User user = new User();
//     user.setName("testuser");
//     customer.setUser(user);
    
//     when(customerRepo.findByName("testuser")).thenReturn(customer);

//     // Act
//     CustomerDTO result = customerService.getCustomerFromName("testuser");

//     // Assert
//     assertEquals("testuser", result.getName());
//     verify(customerRepo, times(1)).findByName("testuser");
// }

    @Test
    public void testGetCustomerFromName_NotFound() {
        // Arrange
        when(customerRepo.findByName("nonexistentuser")).thenReturn(null);

        // Act
        CustomerDTO result = customerService.getCustomerFromName("nonexistentuser");

        // Assert
        assertEquals(null, result);
    }
}
