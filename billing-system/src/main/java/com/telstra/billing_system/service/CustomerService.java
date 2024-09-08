package com.telstra.billing_system.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.repository.CustomerRepository;
import com.telstra.billing_system.repository.InvoiceRepository;
import com.telstra.billing_system.repository.UserRepository;
import java.util.Optional;
 
@Service
public class CustomerService {
    @Qualifier("UserRepository")
    @Autowired
    private UserRepository userRepo;
 
    @Qualifier("CustomerRepository")
    @Autowired
    private CustomerRepository customerRepo;
    @Qualifier("InvoiceRepository")
    @Autowired
    private InvoiceRepository invoiceRepo;
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
    public CustomerDTO getCustomerFromName(String name){
        try {
            System.out.println("hiii");
            Customer response = customerRepo.findByName(name);
            System.out.println(response);
            if(response==null) return null;
            return new CustomerDTO(response);
        } catch (Exception e) {
           System.out.println(e.getMessage());
           return null;
        }
    }
    public Subscription getLastSubscriptionOfACustomer(String customerId){
        try{
            Optional<Invoice> latestInvoice = invoiceRepo.findLatestInvoiceByCustomerId(customerId);
            if(latestInvoice.isEmpty()) return null;
            return latestInvoice.get().getSubscription();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}