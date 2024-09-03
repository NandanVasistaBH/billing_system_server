package com.telstra.billing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.model.SupplierPrincipal;
import com.telstra.billing_system.repository.SupplierRepo;

@Service
@Qualifier("MySupplierDetailsService")
public class MySupplierDetailsService implements UserDetailsService {
    @Autowired
    private SupplierRepo supplierRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user by username: " + username);
        Supplier supplier = supplierRepo.findByName(username);
        if (supplier == null) {
            throw new UsernameNotFoundException("supplier not found with username: " + username);
        }
        return new SupplierPrincipal(supplier); // Ensure this is correctly implemented
    }
}
