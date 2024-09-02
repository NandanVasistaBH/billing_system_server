package com.telstra.billing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.telstra.billing_system.model.Admin;
import com.telstra.billing_system.model.AdminPrincipal;
import com.telstra.billing_system.repository.AdminRepo;

@Primary
@Service
public class MyAdminDetailsService implements UserDetailsService{
    @Autowired
    private AdminRepo adminRepo;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByName(name);
        if (admin == null) {
            throw new UsernameNotFoundException("admin not found with username: " + admin);
        }
        return new AdminPrincipal(admin);
    }
}