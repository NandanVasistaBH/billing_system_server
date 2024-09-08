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
import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.repository.CustomerRepository;
import com.telstra.billing_system.repository.SupplierRepository;
import com.telstra.billing_system.repository.UserRepository;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
@Service("SupplierService")
public class SupplierService {
    @Qualifier("UserRepository")
    @Autowired
    private UserRepository userRepo;

    @Qualifier("SupplierRepository")
    @Autowired
    private SupplierRepository supplierRepo;

    @Qualifier("CustomerRepository")
    @Autowired
    private CustomerRepository customerRepo;
    
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public String register(Supplier supplier) {
        System.out.println(supplier);
        try{
            supplier.getUser().setPassword(encoder.encode(supplier.getUser().getPassword()));
            userRepo.save(supplier.getUser());
            supplierRepo.save(supplier);
            return jwtService.generateToken(supplier.getUser().getName());
        }
        catch(Exception e){
            return e.getMessage();
        }
    }  
    
    public String verify(Supplier supplier) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(supplier.getUser().getName(), supplier.getUser().getPassword())
            );
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(supplier.getUser().getName());
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return "failure";
    }
    public List<CustomerDTO> listOfCustomerOfASupplier(Integer supplierId){
        try {
            Optional<List<Customer>> resp = customerRepo.findBySupplierId(supplierId);
            if(resp.isEmpty()) return null;
            List<CustomerDTO> list = new ArrayList<>();
            for(Customer cust: resp.get()) list.add(new CustomerDTO(cust));
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
