package com.telstra.billing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.repository.SupplierRepository;
import com.telstra.billing_system.repository.UserRepository;

@Service
public class SupplierService {
    @Qualifier("UserRepository")
    @Autowired
    private UserRepository userRepo;

    @Qualifier("SupplierRepository")
    @Autowired
    private SupplierRepository supplierRepo;
    
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
    
}
