package com.telstra.billing_system.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.repository.CustomerRepo;

@Service
public class CustomerService {
    @Autowired
    public CustomerRepo customerRepo;
    
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public String register(Customer customer) {
        customer.setCustPassword(encoder.encode(customer.getCustPassword()));
        customerRepo.save(customer);
        return jwtService.generateToken(customer.getName());
    }  
    
    public String verify(Customer customer) {
        System.out.println("verify service");
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customer.getName(), customer.getCustPassword())
            );
            System.out.println("auth");
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(customer.getName());
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return "failure";
    }
    
}
