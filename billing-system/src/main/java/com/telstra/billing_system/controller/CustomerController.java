package com.telstra.billing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // @PostMapping("/register")
    // public Customer registerCustomer(@RequestBody Customer customer) {
    //     return customerService.registerCustomer(customer);
    // }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Other endpoints
}

