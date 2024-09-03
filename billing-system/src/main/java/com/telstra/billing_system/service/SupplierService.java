package com.telstra.billing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.repository.SupplierRepo;

@Service
public class SupplierService {
    @Autowired
    public SupplierRepo supplierRepo;
    
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

	public String register(Supplier supplier) {
        try {
            supplier.setBranchPassword(encoder.encode(supplier.getBranchPassword()));
            supplierRepo.save(supplier);
            return jwtService.generateToken(supplier.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
           return "failure";
        }
    }

    public String verify(Supplier supplier) {
        System.out.println("verify service");
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(supplier.getName(), supplier.getBranchPassword())
            );
            System.out.println("auth");
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(supplier.getName());
            }
            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "failure";

        }
    }
    
}
