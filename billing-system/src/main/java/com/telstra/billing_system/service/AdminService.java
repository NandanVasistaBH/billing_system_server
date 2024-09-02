package com.telstra.billing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.model.Admin;
import com.telstra.billing_system.repository.AdminRepo;
@Service
public class AdminService {
    @Autowired
    public AdminRepo adminRepo;
    
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;
    
    public String verify(Admin admin) {
        System.out.println("admin service");
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(admin.getName(), admin.getPassword())
        );
        System.out.println(authentication +"   sdc ");
        if( authentication.isAuthenticated()){
            return jwtService.generateToken(admin.getName());
        }
        return "failure";
    }  
}