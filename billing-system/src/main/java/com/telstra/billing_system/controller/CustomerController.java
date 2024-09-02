package com.telstra.billing_system.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.service.CustomerService;
@RestController
public class CustomerController {
    @Autowired
    private CustomerService service;
    @PostMapping("/customer-register")
    public String register(@RequestBody Customer customer){
        System.out.println(customer);
        return service.register(customer);
    }
    @PostMapping("/customer-login")
    public String login(@RequestBody Customer customer){
        System.out.println(customer);
        return service.verify(customer);
    }
    @GetMapping("/hello-world")
    public String helloWorld(){
        System.out.println("asdsadsadas");
        return "hello world";
    }
}
