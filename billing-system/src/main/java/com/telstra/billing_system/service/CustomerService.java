package com.telstra.billing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // @Autowired
    // private BCryptPasswordEncoder passwordEncoder;

    // public Customer registerCustomer(Customer customer) {
    //     customer.setCustPassword(passwordEncoder.encode(customer.getCustPassword()));
    //     return customerRepository.save(customer);
    // }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Other CRUD methods
}
