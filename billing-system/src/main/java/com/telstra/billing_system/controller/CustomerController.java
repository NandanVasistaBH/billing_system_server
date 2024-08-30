package com.telstra.billing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.service.CustomerService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // @PostMapping("/register")
    // public Customer registerCustomer(@RequestBody Customer customer) {
    //     return customerService.registerCustomer(customer);
    // }

    @GetMapping("/api/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        System.out.println("get all the customers");
        return new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
    }

    // Other endpoints
}

