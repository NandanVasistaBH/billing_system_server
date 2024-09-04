package com.telstra.billing_system.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.repository.CustomerRepository;
import com.telstra.billing_system.repository.UserRepository;

@Service
public class CustomerService {
    @Qualifier("UserRepository")
    @Autowired
    private UserRepository userRepo;

    @Qualifier("CustomerRepository")
    @Autowired
    private CustomerRepository customerRepo;
    
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public String register(Customer customer) {
        try{
            customer.getUser().setPassword(encoder.encode(customer.getUser().getPassword()));
            userRepo.save(customer.getUser());
            customerRepo.save(customer);
            return jwtService.generateToken(customer.getUser().getName());
        }
        catch(Exception e){
            return e.getMessage();
        }
    }  
    
    public String verify(Customer customer) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customer.getUser().getName(), customer.getUser().getPassword())
            );
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(customer.getUser().getName());
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return "failure";
    }
    
}
