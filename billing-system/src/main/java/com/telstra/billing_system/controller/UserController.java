package com.telstra.billing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.model.User;
import com.telstra.billing_system.service.UserService;


@RestController
public class UserController {

    @Autowired
    private UserService service;

    // @PostMapping("/register/customer")
    // public User register(@RequestBody Customer cust){
    //     System.out.println(cust);
    //     return service.registerCustomer(cust);
    // }

    // @PostMapping("/register/admin")
    // public User register(@RequestBody Admin admin){
    //     System.out.println(admin);
    //     return service.registerAdmin(admin);
    // }

    // @PostMapping("/register/customer")
    // public User register(@RequestBody Supplier supplier){
    //     System.out.println(supplier);
    //     return service.registerSupplier(supplier);
    // }
    
}