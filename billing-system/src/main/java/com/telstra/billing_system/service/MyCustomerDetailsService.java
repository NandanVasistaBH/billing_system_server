package com.telstra.billing_system.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.CustomerPrincipal;
import com.telstra.billing_system.repository.CustomerRepo;

@Service
public class MyCustomerDetailsService implements UserDetailsService{
    @Autowired
    private CustomerRepo customerRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByName(username);
        if (customer == null) {
            throw new UsernameNotFoundException("customer not found with username: " + username);
        }
        return new CustomerPrincipal(customer);
    }
}